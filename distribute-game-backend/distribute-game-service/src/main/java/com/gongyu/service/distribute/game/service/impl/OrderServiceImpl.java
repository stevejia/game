package com.gongyu.service.distribute.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.factory.OrderModelServer;
import com.gongyu.service.distribute.game.factory.orderModel.DefaultOrderProcess;
import com.gongyu.service.distribute.game.manager.*;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.service.distribute.game.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 17:02
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${order_time_out}")
	private Long orderTimeOut;
	@Autowired
	private List<OrderModelServer> orderModelServers;

	@Autowired
	private UsersService usersService;
	@Autowired
	private PigAwardLogService awardLogService;
	@Autowired
	private PigReservationService pigReservationService;
	@Autowired
	private UserExclusivePigService exclusivePigService;
	@Autowired
	private PigOrderManager orderManager;
	@Autowired
	private PigAwardLogManager awardLogManager;
	@Autowired
	private UserExclusivePigManager pigManager;
	@Autowired
	private RevealLuckyManager luckyManager;
	@Autowired
	private DelayQueueManager delayQueueManager;
	@Autowired
	private AccountLogService accountLogService;

	@Transactional
	@Override
	public BaseResponse createOrder(OrderTypeEnum orderTypeEnum, Object param) {
		log.info("创建订单处理器接收到参数 orderTypeEnum：{}，param:{}", orderTypeEnum, JSON.toJSONString(param));
		OrderModelServer modelServer = orderModelServers.stream().filter(item -> item.isSupport(orderTypeEnum))
				.findFirst().orElse(new DefaultOrderProcess());
		// 按条件开始创建
		modelServer.verif(param);
		// 创建木材实例
		modelServer.createPig(param);
		// 创建订单
		return modelServer.createOrder(param);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void processTask(PigGoods goods) {
		//初始化订单缓存
		RedisUtils2.removeBatch("newOrders:");
		log.info("开始处理开奖逻辑...");
		List<PigOrder> orders;
		List<Long> luckUsers;
		List<PigReservation> reservats = null;
		List<Long> userList = RedisUtils2.getList("robProduct:");
		Set<Long> users = new HashSet<Long>(userList);
		Set<Long> availableUsers = new HashSet<Long>();

//        Set<Long> users = RedisUtils.get("robProduct:" + goods.getId());

		// 本场次所有可出售木材
		List<UserExclusivePig> pigs = exclusivePigService.list(new QueryWrapper<UserExclusivePig>()
				.eq("pig_id", goods.getId()).eq("is_able_sale", SaleStatusEnum.TRUE.getCode())
				.eq("is_pig_lock", LockStatusEnum.NOT_LOCK.getCode()));
		// 删选掉开奖时 用户积分小于抢购积分的用户 避免扣除积分出现负数
		for (Long userId : users) {
			Users user = usersService.getById(userId);
			if (user != null) {
				// 如果当前用户的积分大于等于 木材的抢购积分 则用户为 合法用户
				if (user.getPayPoints() >= goods.getAdoptiveEnergy()) {
					availableUsers.add(userId);
//					boolean hasAppointed = false;
//					for(UserExclusivePig pig: pigs) {
//						if(pig.getAppointUserId().intValue() == userId.intValue()) {
//							hasAppointed = true;
//						}
//					}
//					if(!hasAppointed) {
//						availableUsers.add(userId);
//					}
				}
			}
		}
		users = availableUsers;
		if (CollectionUtils.isEmpty(users)) {
			log.info("木材场次：" + goods.getId() + " 没有用户点击开抢...");
			// 退还所有预约的积分
			PigAwardLog awardLog = awardLogService.getOne(new QueryWrapper<PigAwardLog>().eq("pig_id", goods.getId())
					.eq("open_result", OpenResultEnum.NOT_OPEN));
			reservats = pigReservationService
					.list(new QueryWrapper<PigReservation>().eq("reservation_scene", awardLog.getId()));
			awardLog.setOpenResult(OpenResultEnum.OEPN.getCode());
			awardLogService.updateById(awardLog);
			// 预约用户退还抢购积分-没有点击抢的用户
//			List<Users> userPoints = usersService.convertUserPoints(reservats);
//			if (userPoints != null && userPoints.size() > 0) {
//
//				usersService.saveOrUpdateBatch(userPoints);
//			}
			return;
		}
//        pigManager.addSellIncomePig(pigs);  木材实例在出售收益时生成
		if (!CollectionUtils.isEmpty(pigs) && pigs.size() >= users.size()) {
			log.info("processTask 开奖处理，场次：{}，可出售木材数量：{}", goods.getId(), pigs.size());
			// 获取所有中将用户
			luckUsers = new ArrayList<>(users);
		} else {
			luckUsers = this.getUsers(users, pigs.size());
		}
		log.info("第一次筛选中奖用户:{}", JSON.toJSONString(luckUsers));
		// 本场次所有预约人员
		PigAwardLog awardLog = awardLogService.getOne(
				new QueryWrapper<PigAwardLog>().eq("pig_id", goods.getId()).eq("open_result", OpenResultEnum.NOT_OPEN));
		if (null != awardLog) {
			reservats = pigReservationService
					.list(new QueryWrapper<PigReservation>().eq("reservation_scene", awardLog.getId()));
			log.info("processTask 开奖处理，场次：{}，本场次所有预约人员数量：{}", goods.getId(), reservats.size());
		} else {
			awardLog = awardLogManager.convert(goods.getId(), OpenResultEnum.OEPN);
			awardLog = awardLogManager.insert(awardLog);
		}

		handleDealerOpt(luckUsers, pigs);

		log.info("第二次筛选中奖用户:{}", JSON.toJSONString(luckUsers));
		log.info("筛选开奖木材数量:{}", JSON.toJSONString(pigs.size()));

		// 更新预约记录的抢购状态
		if (null != reservats) {
			reservats = pigReservationService.luckStatus(users, luckUsers, reservats);
			pigReservationService.saveOrUpdateBatch(reservats);
			// 没有中奖的预约用户退还抢购积分
//			List<Users> userPoints = usersService.convertUserPoints2(reservats, goods);
//			if (userPoints != null && userPoints.size() > 0) {
//				usersService.saveOrUpdateBatch(userPoints);
//			}
		}
		// 非预约用户没有抢购到退还积分
//		List<Long> notLuckUsers = this.getNotLuckUsers(users, luckUsers, reservats);
//		for (Long notLuckUserId : notLuckUsers) {
//			Users user = usersService.getById(notLuckUserId);
//			user.setPayPoints(user.getPayPoints() + goods.getAdoptiveEnergy());
//			usersService.updateById(user);
//			accountLogService.convertAndInsert(notLuckUserId, new BigDecimal("0"), new BigDecimal("0"),
//					goods.getAdoptiveEnergy(), new BigDecimal("0"), goods.getGoodsName() + "抢购失败,退回积分",
//					IncomeTypeEnum.RESERVAT, goods.getId(), "", null);
//		}

		for (Long luckUserId : luckUsers) {
			Users user = usersService.getById(luckUserId);
			user.setPayPoints(user.getPayPoints() - goods.getAdoptiveEnergy());
			usersService.updateById(user);
			accountLogService.convertAndInsert(luckUserId, new BigDecimal("0"), new BigDecimal("0"),
					-goods.getAdoptiveEnergy(), new BigDecimal("0"), goods.getGoodsName() + "抢购成功,扣除积分",
					IncomeTypeEnum.RESERVAT, goods.getId(), "", null);

		}

		log.info("processTask 开奖处理，场次：{}，本场抢中用户数量：{}", goods.getId(), luckUsers.size());
		orders = orderManager.createOrder(luckUsers, pigs);
		RedisUtils2.set("newOrders:" + goods.getId(), orders);
		// 插入订单数据
//        if(!CollectionUtils.isEmpty(orders)){
//            orderManager.insertForeach(orders);
//        }
		for (PigOrder order : orders) {
			DelayTask task = luckyManager.convertTaskBase(order, DelayTaskEnum.CANCEL_ORDER, orderTimeOut);
			delayQueueManager.put(task);
			// 防止宕机重启后延时任务消失，将任务加入到缓存
			RedisUtils.set("task:order-" + order.getPigOrderSn(), task, orderTimeOut);
		}
		// 发送短信通知
		if (!CollectionUtils.isEmpty(orders)) {
			log.info("processTask 开奖处理结束,发送短信通知，-------------------------start---------------------场次：{}，本场抢中用户数量：{}",
					goods.getId(), luckUsers.size());
			List<Long> purchaseUserIds = orders.stream().map(PigOrder::getPurchaseUserId).collect(Collectors.toList());
//            usersService.sendSmsCodeThirdPartyBatch(purchaseUserIds, "【航海世界】尊敬的用户，您有新的订单，请尽快审核");
			log.info("processTask 开奖处理结束,发送短信通知，-------------------------end---------------------场次：{}，本场抢中用户数量：{}",
					goods.getId(), luckUsers.size());
		}

		// 更新中奖名单
		awardLogService.handleAwardLog(awardLog, users, new HashSet<>(luckUsers));
		awardLogService.updateById(awardLog);
	}

	/**
	 * 获取非预约并且没有抢购到木材的用户
	 *
	 * @return
	 */
	public List<Long> getNotLuckUsers(Set<Long> robUsers, List<Long> luckUsers, List<PigReservation> reservats) {
		List<Long> notLuckUsers = new LinkedList<>();
		Iterator<Long> iterator = robUsers.iterator();
		log.info("获取非预约并且没有抢购到木材的用户 去除前 users:{}", JSON.toJSONString(robUsers));
		while (iterator.hasNext()) {
			Long next = iterator.next();
			boolean isLuck = false;
			for (int i = 0; i < luckUsers.size(); i++) {
				if (luckUsers.get(i).longValue() == next.longValue()) {
					// notLuckUsers.add(luckUsers.get(i));
					isLuck = true;
					break;
				}
			}
			if (!isLuck) {

				notLuckUsers.add(next);
			}
		}
		// 排除预约失败的用户
		for (PigReservation temp : reservats) {

			if (CommEnum.FALSE.getCode() == temp.getReservationStatus().intValue()) {

				if (notLuckUsers.contains(temp.getUserId())) {

					notLuckUsers.remove(temp.getUserId());
				}
			}
		}
		log.info("获取非预约并且没有抢购到木材的用户 去除前 users:{}", JSON.toJSONString(notLuckUsers));
		return notLuckUsers;
	}

	/**
	 * 处理指定开奖用户、将指定开奖用户加入到对应的中奖人员列表，同时取消一个中奖人员
	 */
	public void handleDealerOpt(List<Long> luckUsers, List<UserExclusivePig> pigs) {
		int count = 0;
		for (int i = 0; i < pigs.size(); i++) {
			if (null != pigs.get(i).getAppointUserId() && 0 != pigs.get(i).getAppointUserId().longValue()) {
				luckUsers.set(count, pigs.get(i).getAppointUserId());
				count = count + 1;
			}
		}
	}

	/**
	 * 将参与用户分两组 新玩家组 、 老玩家组
	 *
	 * @param users
	 * @return index：0 新玩家 1 老玩家
	 */
	public List<Long> getUsers(Set<Long> users, Integer pigs) {
		// 开始为抢购的人分配木材 用户暂时按照ID序号分新玩家/老玩家 并随机取出中奖人
		Set<Long> oldUsers = new HashSet<>();
		Set<Long> youngUsers = new HashSet<>();
		List<Long> list = new ArrayList<>(users);
		int point;

		if (this.mold(users.size())) {
			point = users.size() / 2;
		} else {
			point = (users.size() - 1) / 2;
		}

		// 计算分配两份木材的数量
		int youngNum;
		int oldNum;
		if (this.mold(pigs)) {
			youngNum = pigs / 2;
			oldNum = youngNum;
		} else {
			youngNum = (pigs - 1) / 2;
			oldNum = youngNum + 1;
		}
		for (int i = 0; i < point; i++) {
			youngUsers.add(list.get(i));
		}
		for (int i = point; i < users.size(); i++) {
			oldUsers.add(list.get(i));
		}
		List randomYoungUser = getRandomList(new ArrayList(youngUsers), youngNum);
		List randomOldUsers = getRandomList(new ArrayList(oldUsers), oldNum);
		randomOldUsers.addAll(randomYoungUser);
		return randomOldUsers;
	}

	/**
	 * 余运算 true=偶数 false=奇数
	 *
	 * @param num
	 * @return
	 */
	public static boolean mold(int num) {
		if (num % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从list中随机抽取若干不重复元素
	 *
	 * @param paramList:被抽取list
	 * @param count:抽取元素的个数
	 * @return:由抽取元素组成的新list
	 */
	public static List getRandomList(List paramList, int count) {
		if (paramList.size() < count) {
			return paramList;
		}
		Random random = new Random();
		List<Integer> tempList = new ArrayList<Integer>();
		List<Object> newList = new ArrayList<Object>();
		int temp = 0;
		for (int i = 0; i < count; i++) {
			temp = random.nextInt(paramList.size());// 将产生的随机数作为被抽list的索引
			if (!tempList.contains(temp)) {
				tempList.add(temp);
				newList.add(paramList.get(temp));
			} else {
				i--;
			}
		}
		return newList;
	}
}
