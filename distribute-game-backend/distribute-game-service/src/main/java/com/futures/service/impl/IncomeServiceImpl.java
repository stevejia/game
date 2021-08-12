package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.futures.common.enums.IncomeTypeEnum;
import com.futures.common.enums.LevelIncomEnum;
import com.futures.common.enums.RecomLevelEnum;
import com.futures.common.enums.SaleStatusEnum;
import com.futures.common.utils.DateUtil;
import com.futures.manager.UserExclusivePigManager;
import com.futures.mapper.PigGoodsMapper;
import com.futures.model.entity.*;
import com.futures.service.*;
import com.gongyu.snowcloud.framework.util.DateUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/30 16:41
 */
@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private UsersService usersService;
	@Autowired
	private UserExclusivePigManager pigManager;
	@Autowired
	private AccountLogService logService;
	@Autowired
	private PigGoodsService goodsService;
	@Autowired
	private PigGoodsMapper goodsMapper;
	@Autowired
	private PigOrderService orderService;
	@Autowired
	private UserLevelService levelService;
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ExpenseLogService expenseLogService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void processIncome() {
		List<ContractIncom> contractIncoms = new ArrayList<>();
		List<PigGoods> allPigGoods = goodsMapper.selectList(null);
		PigGoods maxPriceGoods = goodsMapper.findMaxPrice();
		// 合约收入
//        BigDecimal conIncome = new BigDecimal("0");
		List<UserExclusivePig> pigs = pigManager.findListByUser();
		Long todayZero = this.getDateZeroTime(0);
		Long tommorowZero = this.getDateZeroTime(1);
//		List<Long> userIdList = new LinkedList<Long>();
		for (UserExclusivePig pig : pigs) {
			Long day = DateUtil
					.getDay(DateUtils.format(DateUtil.getDate(pig.getBuyTime()), DateUtils.DEFAULT_DATE_FORMAT) + " 00:00:00");
			if (day < 1) {
				continue;
			}
			
			if (pig.getEndTime().longValue() < tommorowZero.longValue() && pig.getEndTime() > todayZero) {
				
				// 根据pigId取得订单
				PigOrder order = orderService.getOne(new QueryWrapper<PigOrder>().eq("pig_id", pig.getId()));
				BigDecimal realIncome = BigDecimal.ZERO;
				BigDecimal totalIncome = order.getPigPrice().multiply(pig.getNowIncomeRatio().divide(BigDecimal.valueOf(100)));
				//做差异计算逻辑 为了平衡 用户的收益信息
				BigDecimal currentIncome = pig.getPrice().subtract(order.getPigPrice());
				//历史记录处理 升值几天后 最后一天出来的时候 根据应该的收益-已经升值后的收益=真实收益 用于 log保存&用户总收益累加
				realIncome = totalIncome.subtract(currentIncome);
				pig.setPrice(order.getPigPrice().add(totalIncome));
				
				
				//升值结束 判断木材是否升级到下一轮
				this.processUpgrade(pig, allPigGoods);
				pig.setIsAbleSale(SaleStatusEnum.TRUE.getCode());
				//合约到期 木材如果需要分裂 那么执行分裂逻辑
				// 木材是否分裂
				if (pig.getPrice().compareTo(maxPriceGoods.getLargePrice()) > 0) {
					pigManager.splitPig(pig, maxPriceGoods);
				}
				
				pigManager.update(pig);
				
				//根据产品的userID 获取用户记录
				Users user = usersService.getById(pig.getUserId());
//				userIdList.add(user.getId());
				//插入合约收益记录
				logService.convertAndInsert(user.getId(), new BigDecimal("0"), new BigDecimal("0"), 0, realIncome, "合约收益",
						IncomeTypeEnum.CONTRACT_INCOM, pig.getPigId(), order.getPigOrderSn(), null);
				
				ContractIncom contractIncom = new ContractIncom();
				contractIncom.setUsers(user);
				contractIncom.setContractIncom(realIncome);
				contractIncoms.add(contractIncom);
				user.setAccumulatedIncome(user.getAccumulatedIncome().add(realIncome));
				user.setDogeMoney(user.getDogeMoney() + 1);
				usersService.updateById(user);
			}
		}
		
//		userIdList = userIdList.stream().distinct().collect(Collectors.toList());
//		
//		List<ContractIncom> sumContractIncoms = new LinkedList<ContractIncom>();
//		
//		userIdList.forEach(userId -> {
//			List<ContractIncom> matchedIncoms = contractIncoms.stream().filter(incom->{
//				return incom.getUsers().getId().intValue() == userId.intValue();
//			}).collect(Collectors.toList());
//			
//			BigDecimal sumIncom = BigDecimal.ZERO;
//			
//			Users user = matchedIncoms.get(0).getUsers();
//			ContractIncom contractIncom = new ContractIncom();
//			contractIncom.setUsers(user);
//			contractIncom.setContractIncom(BigDecimal.ZERO);
//			matchedIncoms.forEach(incom->{
//				contractIncom.setContractIncom(contractIncom.getContractIncom().add(incom.getContractIncom()));
//			});
//			sumContractIncoms.add(contractIncom);
//		});
		
		// 计算上级提成收\团队提成收益
		((IncomeServiceImpl) AopContext.currentProxy()).calcuIncome(contractIncoms);

	}

	public Long getDateZeroTime(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, offset);

		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 判断木材是否升级到下一阶段
	 * 
	 * @param pig
	 */
	private void processUpgrade(UserExclusivePig pig, List<PigGoods> allPigGoods) {
		BigDecimal price = pig.getPrice();
		for (PigGoods pigGoods : allPigGoods) {
			if(pigGoods.getSmallPrice().compareTo(price)<=0 && pigGoods.getLargePrice().compareTo(price)>0) {
				pig.setPigId(pigGoods.getId());
				pig.setNowContractDays(pigGoods.getContractDays());
				pig.setNowIncomeRatio(pigGoods.getIncomeRatio());
			}
		}
	}

	/**
	 * 计算上级提成收\团队提成收益
	 * 
	 * @param contractIncoms
	 */
	public void calcuIncome(List<ContractIncom> contractIncoms) {
		// 推荐收益
		Config firstLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "first_rate"));
		Config secondLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "second_rate"));
		Config thirdLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "third_rate"));
		
		List<Users> allIncoms = new LinkedList<Users>();
		
		Map<Long, BigDecimal> incomMap = new HashMap<Long, BigDecimal>();
		
		
		
		for (ContractIncom incom : contractIncoms) {
			Users user = incom.getUsers();
			Long firstLeaderId = user.getFirstLeader();
			if (null != firstLeaderId && firstLeaderId > 0) {
				BigDecimal recomIncome = this.calcuRecomIncome(firstLeader.getConfigValue(), incom.getContractIncom());
				Users incomUser = new Users();
				incomUser.setId(firstLeaderId);
				incomUser.setRecomIncome(recomIncome);
				
				allIncoms.add(incomUser);
				
				incomMap.put(firstLeaderId, recomIncome);
//				((IncomeServiceImpl) AopContext.currentProxy()).updateLeaderIncome(firstLeaderId, recomIncome);
			}
			Long secondLeaderId = user.getSecondLeader();
			if (null != secondLeaderId && secondLeaderId > 0) {
				BigDecimal recomIncome = this.calcuRecomIncome(secondLeader.getConfigValue(), incom.getContractIncom());
				Users incomUser = new Users();
				incomUser.setId(secondLeaderId);
				incomUser.setRecomIncome(recomIncome);
				
				allIncoms.add(incomUser);
				incomMap.put(secondLeaderId, recomIncome);
				
//				((IncomeServiceImpl) AopContext.currentProxy()).updateLeaderIncome(secondLeaderId, recomIncome);
			}
			Long thirdLeaderId = user.getThirdLeader();
			if (null != thirdLeaderId && thirdLeaderId > 0) {
				BigDecimal recomIncome = this.calcuRecomIncome(thirdLeader.getConfigValue(), incom.getContractIncom());
				Users incomUser = new Users();
				incomUser.setId(thirdLeaderId);
				incomUser.setRecomIncome(recomIncome);
				
				allIncoms.add(incomUser);
				incomMap.put(firstLeaderId, recomIncome);
//				((IncomeServiceImpl) AopContext.currentProxy()).updateLeaderIncome(thirdLeaderId, recomIncome);
			}


			
			if (null != firstLeaderId && firstLeaderId > 0) {
				// 计算团队收益
				((IncomeServiceImpl) AopContext.currentProxy()).calcuTeamIncome(incom.getContractIncom(), user,
						firstLeaderId);
			}
		}
		
		Map<Long, BigDecimal> sumIncomMap = new HashMap<Long, BigDecimal>();
		List<Long> disinctIds = allIncoms.stream().map(item-> item.getId()).distinct().collect(Collectors.toList());
		disinctIds.forEach(id->{
			List<Users> testUsers =  allIncoms.stream().filter(item->item.getId().intValue() == id.intValue()).collect(Collectors.toList());
			BigDecimal incoms = allIncoms.stream().filter(item->item.getId().intValue() == id.intValue()).map(item-> item.getRecomIncome()).reduce(BigDecimal.ZERO,BigDecimal::add);
			sumIncomMap.put(id, incoms);
		});
		
		sumIncomMap.entrySet().forEach(item->{
			((IncomeServiceImpl) AopContext.currentProxy()).updateLeaderIncome(item.getKey(), item.getValue());
		});
	}

	/**
	 * 上级团队收益
	 * 
	 * @param
	 * @param superLeaderId
	 */
	public void calcuTeamIncome(BigDecimal income, Users user, Long superLeaderId) {
		Users superLeader = usersService.getById(superLeaderId);
		UserLevel level = levelService.getById(superLeader.getLevel());
		LevelIncomEnum anEnum = LevelIncomEnum.parse(superLeader.getLevel());
		if (anEnum.isTeam()) {
			if (superLeader.getLevel() > user.getLevel()) {
				BigDecimal superIncom = income.multiply(level.getTeamAward()).divide(new BigDecimal("100"), 2,
						RoundingMode.HALF_UP);
				logService.convertAndInsert(superLeaderId, new BigDecimal("0"), new BigDecimal("0"), 0, superIncom,
						"团队收益提成", IncomeTypeEnum.TEAM_WILL, 0L, StringUtils.EMPTY, null);
				superLeader.setRecomIncome(superLeader.getRecomIncome().add(superIncom));
				usersService.updateById(superLeader);
			}
		}
		if (null != superLeader.getFirstLeader() && superLeader.getFirstLeader() > 0) {
			this.calcuTeamIncome(income, user, superLeader.getFirstLeader());
		}
	}

	/**
	 * 根据用户收益更新上级收益
	 * 
	 * @param leaderId
	 * @param recomIncome
	 */
	public void updateLeaderIncome(Long leaderId, BigDecimal recomIncome) {
		Users leader = usersService.getById(leaderId);
		logService.convertAndInsert(leader.getId(), new BigDecimal("0"), new BigDecimal("0"), 0, recomIncome, "推荐收益提成",
				IncomeTypeEnum.RECOMM_WILL, 0L, StringUtils.EMPTY, null);
		leader.setRecomIncome(recomIncome.add(leader.getRecomIncome()));
		usersService.updateById(leader);
	}

	/**
	 * 计算推广收益
	 * 
	 * @return
	 */
	public BigDecimal calcuRecomIncome(String rate, BigDecimal income) {
		BigDecimal incomeRate = new BigDecimal(rate).divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
		BigDecimal multiply = income.multiply(incomeRate);
		return multiply;
	}

	/**
	 * 计算pig合约收益
	 * 
	 * @param pigId 木材ID
	 * @param price 木材现在价格
	 * @return
	 */
	public BigDecimal calcuPigIncom(Long pigId, BigDecimal price) {
		PigGoods goods = goodsService.getById(pigId);
		// 单日收益率
		BigDecimal divide = goods.getIncomeRatio().divide(new BigDecimal(String.valueOf(goods.getContractDays())), 10,
				RoundingMode.HALF_UP);
		BigDecimal incomeRatio = divide.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
		BigDecimal income = price.multiply(incomeRatio);
		return income;
	}
}
