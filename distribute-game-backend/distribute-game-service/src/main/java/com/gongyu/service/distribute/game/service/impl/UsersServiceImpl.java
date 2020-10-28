package com.gongyu.service.distribute.game.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gongyu.service.distribute.game.common.enums.CommEnum;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.RechargeMapper;
import com.gongyu.service.distribute.game.mapper.UsersMapper;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.service.distribute.game.utils.CalculateUtils;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.util.MD5;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UsersServiceImpl extends CrudServiceSupport<UsersMapper, Users> implements UsersService {

	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private SysSmsFpi sysSmsFpi;
	@Autowired
	private UserLevelService userLevelService;
	@Autowired
	private RechargeMapper rechargeMapper;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UserPaymentService userPaymentService;
	@Autowired
	private UserIdentityService userIdentityService;
	@Autowired
	private PigAppealService pigAppealService;
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private PigOrderService pigOrderService;

	@Autowired
	private UserExclusivePigService userExclusivePigService;

	@Override
	public IPage<Users> queryUsers(IPage<Users> page, String mobile, Long id, Long regTimeStart, Long regTimeEnd) {
		LambdaQueryWrapper<Users> eq = new LambdaQueryWrapper<>();
		if (!StringUtils.isEmpty(mobile)) {
			eq.eq(Users::getMobile, mobile);
		}
		if (id != null) {
			eq.eq(Users::getId, id);
		}

		if (regTimeStart != null && regTimeEnd != null) {
			eq.ge(Users::getRegTime, regTimeStart);
			eq.le(Users::getRegTime, regTimeEnd);
		}

		return this.page(page, eq);
	}

	@Override
	public List<Users> queryAllUsers() {
		return this.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public Integer saveUsers(UsersSaveDto usersSaveDto) {
		// 用户手机号查询
		boolean exists = this.exists(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, usersSaveDto.getMobile()));
		if (exists) {
			throw new BizException("当前手机号已存在，请勿重复添加");
		}
		int timeStamp = Math.toIntExact(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)).getEpochSecond());

		List<Users> registeredUsers = this.list();
		int i = this.genUserCode(registeredUsers);

		UsersSaveDto bean = UsersSaveDto.builder().code(i).regTime(timeStamp).createTime(timeStamp)
				.lastLoginTime(timeStamp).lastLoginIp(WebUtils.getClientIp()).pushId("").emailValidated(0)
				.messageMask(i).nickname(usersSaveDto.getNickname()).mobile(usersSaveDto.getMobile())
				.password(usersSaveDto.getPassword()).paypwd(usersSaveDto.getPaypwd())
				.firstLeader(usersSaveDto.getFirstLeader()).secondLeader(usersSaveDto.getSecondLeader())
				.firstLeader(usersSaveDto.getThirdLeader()).userType(1)// 普通用户
				.build();
		bean.setFirstLeader(usersSaveDto.getFirstLeader());
		bean.setSecondLeader(usersSaveDto.getSecondLeader());
		bean.setThirdLeader(usersSaveDto.getThirdLeader());
		Users users = new Users();

		BeanUtils.copyProperties(bean, users);
		if (null != bean.getFirstLeader()) {
			users.setFirstLeader(Long.valueOf(bean.getFirstLeader()));
		}
		if (null != bean.getSecondLeader()) {
			users.setSecondLeader(Long.valueOf(bean.getSecondLeader()));
		}
		if (null != bean.getThirdLeader()) {
			users.setThirdLeader(Long.valueOf(bean.getThirdLeader()));
		}
		return usersMapper.insert(users);
	}

	private int genUserCode(List<Users> users) {
		int code = ThreadLocalRandom.current().nextInt(100000, 999999);
		boolean exist = true;
		exist = false;
		boolean isExist = exist;
		users.forEach(user -> {
			if (user.getCode() == code) {
				this.genUserCode(users);
				return;
			}
		});
		return code;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void modifyUsersFirstLeader(UsersModifyDto usersModifyDto) {
		Users newFirstLeader = getById(usersModifyDto.getFirstLeader());
		if (newFirstLeader == null) {

			throw new BizException("上级不存在！");
		}
		Users self = getById(usersModifyDto.getId());
		if (self == null) {

			throw new BizException("目标用户不存在！");
		}
		self.setFirstLeader(newFirstLeader.getId());
		self.setSecondLeader(newFirstLeader.getFirstLeader());
		self.setThirdLeader(newFirstLeader.getSecondLeader());

		List<Users> sons = list((Wrapper) (new QueryWrapper()).eq("first_leader", usersModifyDto.getId()));
		List<Users> grandsons = list((Wrapper) (new QueryWrapper()).eq("second_leader", usersModifyDto.getId()));

		if (sons != null && sons.size() > 0) {

			for (Users son : sons) {

				if (son != null) {

					son.setSecondLeader(self.getFirstLeader());
					son.setThirdLeader(self.getSecondLeader());
					this.updateById(son);
				}

			}
		}
		if (grandsons != null && grandsons.size() > 0) {

			for (Users grandson : grandsons) {

				if (grandson != null) {

					grandson.setThirdLeader(newFirstLeader.getId());
					this.updateById(grandson);
				}

			}
		}
		this.updateById(self);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void modifyUsersInfo(UsersModifyDto usersModifyDto) {
		Users users = getById(usersModifyDto.getId());
		users.setNickname(usersModifyDto.getNickname());
		users.setLevel(usersModifyDto.getLevel());
		users.setPassword(usersModifyDto.getPassword());
		users.setPaypwd(usersModifyDto.getPaypwd());
		users.setIdentity(usersModifyDto.getIdentity());
		users.setRuleSort(usersModifyDto.getRuleSort());
		users.setIsLock(usersModifyDto.getIsLock());
		this.updateById(users);
	}

	@Override
	public void modifyAccount(Integer userId, int type, BigDecimal score, int direction, String remark,
			IncomeTypeEnum incomeTypeEnum) {
		if (type == 2) {
			// 积分增减
			boolean b = this.modifyPayPoints(userId, score.intValue(), direction, remark, incomeTypeEnum);
			if (!b) {
				throw new BizException("扣减积分失败");
			}
		}
		if (type == 1) {
			// 收益增减
			this.modifyUserMoney(userId, score, direction, remark, incomeTypeEnum);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public boolean modifyUserMoney(Integer userId, BigDecimal score, int direction, String remark,
			IncomeTypeEnum incomeTypeEnum) {
		if (score.compareTo(BigDecimal.ZERO) < 0) {
			log.info(userId + " modifyUserMoney score=" + score + " 无效的推广收益");
			return false;
		}
		Users users = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getId, userId));
		Assert.notNull(users, "无效的会员ID:" + userId);
		BigDecimal before = users.getRecomIncome();
		double after = 0;
		if (direction == 1) {
			after = CalculateUtils.add(before.doubleValue(), score.doubleValue());
		} else if (direction == 2) {
			if (before.compareTo(score) < 0) {
				return false;
			}
			score = score.multiply(new BigDecimal("1").negate());
			after = CalculateUtils.add(before.doubleValue(), score.doubleValue());
		} else {
			return false;
		}
		BigDecimal bigDecimal = new BigDecimal(after);
		users.setRecomIncome(bigDecimal);
		this.updateById(users);
		// 积分操作记录
		AccountLogSaveDto build = AccountLogSaveDto.builder().userId(Long.valueOf(userId))
				.userMoney(new BigDecimal("0")).dogeMoney(0).pigCurrency(0).changeTime(DateUtil.getNowDate())
				.desc(remark).build();
		if (incomeTypeEnum.equals(IncomeTypeEnum.PROMOTE)) {
			build.setContractRevenue(score);
			build.setDesc("推广收益");
		} else {
			build.setUserMoney(score);
		}
		build.setType(incomeTypeEnum.getCode());
		accountLogService.saveAccountLog(build);
		log.info("[推广收益追踪]会员ID:{},推广收益{}:{},变动说明:{}", userId, direction == 1 ? "增加" : "减少", score, remark);
		return true;
	}

	@Override
	public List<UsersTreeResponseDto> queryTreeList(Long userId, String type) {
		List<UsersTreeResponseDto> list = usersMapper.queryTreeList(userId, type);
		return list;
	}

	@Override
	public List<Users> convertUserPoints(List<PigReservation> reservats) {
		List<Users> users = new ArrayList<>();
		for (PigReservation reservat : reservats) {
			Users user = this.getById(reservat.getUserId());
			if (CommEnum.FALSE.getCode() == reservat.getReservationStatus().intValue()) {
				user.setPayPoints(user.getPayPoints() + reservat.getPayPoints());
				users.add(user);
				accountLogService.convertAndInsert(user.getId(), new BigDecimal("0"), new BigDecimal("0"),
						reservat.getPayPoints(), new BigDecimal("0"), "抢购失败,退回积分", IncomeTypeEnum.RESERVAT,
						reservat.getPigId(), "", null);
			}
		}
		return users;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRED)
	public List<Users> convertUserPoints2(List<PigReservation> reservats, PigGoods goods) {
		Integer adoptiveEnergy = goods.getAdoptiveEnergy();
		List<Users> users = new ArrayList<>();
		for (PigReservation reservat : reservats) {
			Users user = this.getById(reservat.getUserId());
			if (CommEnum.FALSE.getCode() == reservat.getReservationStatus().intValue()) {
				if (reservat.getIsClickBuy().intValue() == CommEnum.TRUE.getCode()) {
					user.setPayPoints(user.getPayPoints() + adoptiveEnergy);
					users.add(user);
					accountLogService.convertAndInsert(user.getId(), new BigDecimal("0"), new BigDecimal("0"),
							adoptiveEnergy, new BigDecimal("0"), goods.getGoodsName() + "抢购失败,退回积分(预约点击抢购用户)", IncomeTypeEnum.RESERVAT,
							reservat.getPigId(), "", null);
				} else if (reservat.getIsClickBuy().intValue() == CommEnum.FALSE.getCode()) {
					user.setPayPoints(user.getPayPoints() + reservat.getPayPoints());
					users.add(user);
					accountLogService.convertAndInsert(user.getId(), new BigDecimal("0"), new BigDecimal("0"),
							reservat.getPayPoints(), new BigDecimal("0"), goods.getGoodsName() + "抢购失败,退回积分(预约未点击抢购用户)",
							IncomeTypeEnum.RESERVAT, reservat.getPigId(), "", null);
				}

			}
		}
		return users;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public boolean modifyPayPoints(Integer userId, int score, int direction, String remark,
			IncomeTypeEnum incomeTypeEnum) {
		if (score <= 0) {
			log.info(userId + " modifyAccountScore score=" + score + " 无效的积分");
			return false;
		}
		// 积分扣减
		Users users = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getId, userId));
		Assert.notNull(users, "无效的会员ID:" + userId);
		int before = users.getPayPoints();
		int after = 0;
		if (direction == 1) {
			after = before + score;
		} else if (direction == 2) {
			if (before < score) {
				return false;
			}
			after = before - score;
		} else {
			return false;
		}
		users.setPayPoints(after);
		updateById(users);
		// 积分操作记录
		if (2 == direction) {
			score *= -1;
		}
		AccountLogSaveDto build = AccountLogSaveDto.builder().userId(Long.valueOf(userId)).payPoints(score).dogeMoney(0)
				.pigCurrency(0).changeTime(DateUtil.getNowDate()).desc(remark).build();
		if (incomeTypeEnum.equals(IncomeTypeEnum.PROMOTE)) {
			build.setDesc(direction == 1 ? "增加积分" : "减少积分");
		}
		build.setType(IncomeTypeEnum.SYS_POINT.getCode());
		accountLogService.saveAccountLog(build);
		log.info("[积分追踪]会员ID:{},积分{}:{},变动说明:{}", userId, direction == 1 ? "增加" : "减少", score, remark);
		return true;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public boolean modifyPayPoints(Integer userId, int score, int direction, String remark,
			IncomeTypeEnum incomeTypeEnum, PigGoods goods) {
		if (score <= 0) {
			log.info(userId + " modifyAccountScore score=" + score + " 无效的积分");
			return false;
		}
		// 积分扣减
		Users users = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getId, userId));
		Assert.notNull(users, "无效的会员ID:" + userId);
		int before = users.getPayPoints();
		int after = 0;
		if (direction == 1) {
			after = before + score;
		} else if (direction == 2) {
			if (before < score) {
				return false;
			}
			after = before - score;
		} else {
			return false;
		}
		users.setPayPoints(after);
		updateById(users);
		// 积分操作记录
		if (2 == direction) {
			score *= -1;
		}
		AccountLogSaveDto build = AccountLogSaveDto.builder().userId(Long.valueOf(userId)).payPoints(score).dogeMoney(0)
				.pigCurrency(0).changeTime(DateUtil.getNowDate()).desc(remark).build();
		if (incomeTypeEnum.equals(IncomeTypeEnum.PROMOTE)) {
			build.setDesc(direction == 1 ? "增加积分" : "减少积分");
		}
		build.setType(IncomeTypeEnum.SYS_POINT.getCode());
		build.setPigId(goods.getId());
		accountLogService.saveAccountLog(build);
		log.info("[积分追踪]会员ID:{},积分{}:{},变动说明:{}", userId, direction == 1 ? "增加" : "减少", score, remark);
		return true;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public MemberLoginResponseDto login(String mobile, String password, String codeKey, String codeValue,
			String loginIp) {
		String graphVerifyCode = RedisUtils.get(codeKey);
		if (StringUtils.isEmpty(graphVerifyCode)) {
			throw new BizException("图形验证码失效");
		}
		if (!codeValue.equals(graphVerifyCode)) {
			throw new BizException("图形验证码错误");
		}
		Users user = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, mobile));
		if (user == null) {
			throw new BizException("账号不存在");
		}
		if (!user.getPassword().equals(MD5.string2MD5(password))) {
			throw new BizException("密码错误");
		}
		if (user.getIsLock() == 1) {
			throw new BizException("账号异常已被锁定");
		}
		// 设置登录token 如果为空则未登录
		user.setToken(MD5.getMD5Code(String.valueOf(DateUtils.currentDate().getTime())));
		user.setLastLoginTime(DateUtils.currentDate().getTime());
		this.updateById(user);
		MemberLoginResponseDto build = MemberLoginResponseDto.builder().mobile(user.getMobile()).token(user.getToken())
				.userId(Long.valueOf(user.getId())).code(user.getCode()).build();
		return build;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public MemberLoginResponseDto login(String mobile) {
		Users user = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, mobile));
		// 设置登录token 如果为空则未登录
		user.setToken(MD5.getMD5Code(String.valueOf(DateUtils.currentDate().getTime())));
		user.setLastLoginTime(DateUtils.currentDate().getTime());

		WebUtils.setCurrentUserId(user.getId());
		this.updateById(user);
		MemberLoginResponseDto build = MemberLoginResponseDto.builder().mobile(user.getMobile()).token(user.getToken())
				.userId(Long.valueOf(user.getId())).code(user.getCode()).build();
		return build;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void logout() {
		Users user = this.getById(WebUtils.getCurrentUserId());
		user.setToken("");
		user.setLastLoginTime(0L);
		this.updateById(user);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public Long create(MemberLoginRequestDto memberLoginRequestDto) {
		/*
		 * // 图形验证码校验 String graphVerifyCode =
		 * RedisUtils.get(memberLoginRequestDto.getCodeKey()); if
		 * (StringUtils.isEmpty(graphVerifyCode)) { throw new BizException("图形验证码失效"); }
		 */
		// 短信校验
//        boolean b = sysSmsFpi.checkSmsCode(memberLoginRequestDto.getMobile(),
//                memberLoginRequestDto.getSmsCode(), PhoneCodeTypeEnum.REGISTER.getCode());
//        if (!b) {
//            throw new BizException("短信验证码错误");
//        }
		if (memberLoginRequestDto.getFirstLeader() == null) {
			throw new BizException("邀请人ID不能为空");
		}

		Users inviteUser = this
				.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getCode, memberLoginRequestDto.getFirstLeader()));
		if (inviteUser == null) {
			throw new BizException("推荐人ID不存在");
		}
		// 将firstLeader的值Code 改成用户ID
		memberLoginRequestDto.setFirstLeader(inviteUser.getId().intValue());
		// 俩次密码校验
		if (!memberLoginRequestDto.getPassword().equals(memberLoginRequestDto.getOncePassword())) {
			throw new BizException("请输入相同的【登录密码】");
		}
		if (!memberLoginRequestDto.getPaypwd().equals(memberLoginRequestDto.getOncePaypwd())) {
			throw new BizException("请输入相同的【支付密码】");
		}
		// 手机号重复项校验
		Users one = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, memberLoginRequestDto.getMobile()));
		if (one != null) {
			throw new BizException("手机号已注册");
		}
		// 分销关系建立
		UsersSaveDto build = UsersSaveDto.builder().mobile(memberLoginRequestDto.getMobile())
				.nickname(memberLoginRequestDto.getNickname())
				.password(MD5.getMD5Code(memberLoginRequestDto.getPassword()))
				.paypwd(MD5.getMD5Code(memberLoginRequestDto.getPaypwd())).build();
		if (null != memberLoginRequestDto.getFirstLeader()) {
			build.setFirstLeader(memberLoginRequestDto.getFirstLeader());
			Users fristLeader = this.getById(memberLoginRequestDto.getFirstLeader());
			if (null != fristLeader) {
				Users twoLeader = this.getById(fristLeader.getFirstLeader());
				if (null != twoLeader) {
					build.setSecondLeader(Math.toIntExact(twoLeader.getId()));
					Users threeLeader = this.getById(twoLeader.getFirstLeader());
					if (null != threeLeader) {
						build.setThirdLeader(Math.toIntExact(threeLeader.getId()));
					}
				}

			}
			// 检查上级推广人数是否达到升级标准
			checkUpgrade(Long.valueOf(memberLoginRequestDto.getFirstLeader()));
		}
		int isSucess = this.saveUsers(build);
		Users user = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, build.getMobile()));
		// 注册赠送积分
		try {
			if (isSucess > 0 && user != null) {

				accountLogService.giveAccountForRegister(user.getId());
			}

		} catch (Exception e) {

		}

		return Long.valueOf(isSucess);
	}

	@Override
	public void sendSmsCode(String phone, String codeType, String smsTemplate, Map<String, String> templateParam) {
		sysSmsFpi.sendSmsCode(phone, codeType, smsTemplate, templateParam);
	}

	@Override
	public void sendSmsCodeThirdParty(String phone, String codeType, String content, String code) {
		sysSmsFpi.sendSmsCodeThirdParty(phone, codeType, content, code);
	}

	@Override
	public void sendSmsCodeThirdPartyBatch(List<Long> ids, String content) {
		Collection<Users> users = this.listByIds(ids);
		users.stream().forEach(e -> sysSmsFpi.sendSms(e.getMobile(), content));
	}

	@Override
	public GraphVerifyCodeDto getGraphVerifyCode() {
		String value = RandomStringUtils.randomNumeric(4);
		String uuid = value + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		GraphVerifyCodeDto verifyCodeDto = new GraphVerifyCodeDto();
		verifyCodeDto.setCodeKey(uuid);
		verifyCodeDto.setCodeValue(value);
		RedisUtils.set(uuid, value, 180L);
		return verifyCodeDto;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void fgPwd(MemberLoginRequestDto memberLoginRequestDto) {
		Users user = this.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getId, memberLoginRequestDto.getUId()));
		if (user == null) {
			throw new BizException("用户信息不存在");
		}
		if (memberLoginRequestDto.getOperationType() == 1
				&& !MD5.getMD5Code(memberLoginRequestDto.getOldPassword()).equals(user.getPassword())) {
			throw new BizException("输入的当前登录密码不正确");
		}
		if (memberLoginRequestDto.getOperationType() == 2
				&& !MD5.getMD5Code(memberLoginRequestDto.getOldPayPwd()).equals(user.getPaypwd())) {
			throw new BizException("输入的当前交易密码不正确");
		}
		// 俩次登录密码校验
		if (memberLoginRequestDto.getPassword() != null) {
			if (!memberLoginRequestDto.getPassword().equals(memberLoginRequestDto.getOncePassword())) {
				throw new BizException("请输入相同的【登录密码】");
			}
			user.setToken("");
			user.setLastLoginTime(DateUtils.currentDate().getTime());
			user.setPassword(MD5.getMD5Code(memberLoginRequestDto.getPassword()));
		}
		// 俩次交易密码校验
		if (memberLoginRequestDto.getPaypwd() != null) {
			if (!memberLoginRequestDto.getPaypwd().equals(memberLoginRequestDto.getOncePaypwd())) {
				throw new BizException("请输入相同的【交易密码】");
			}
			user.setPaypwd(MD5.getMD5Code(memberLoginRequestDto.getPaypwd()));
		}
//		boolean b = sysSmsFpi.checkSmsCode(memberLoginRequestDto.getMobile(), memberLoginRequestDto.getSmsCode(),
//				memberLoginRequestDto.getCodeType());
//		if (!b) {
//			throw new BizException("短信验证码失效");
//		}
		usersMapper.updateById(user);
	}

	@Override
	public void updateUser(Long id, String userName) {
		Users user = this.getById(id);
		if (user == null) {
			throw new BizException("用户信息不存在");
		}
		List<Users> list = this.list(Wrappers.<Users>lambdaQuery().eq(Users::getNickname, userName));
		if (!CollectionUtil.isEmpty(list)) {
			throw new BizException("用户名已存在");
		}
		user.setNickname(userName);
		usersMapper.updateById(user);
	}

	@Override
	public Users queryUser(Long currentUserId) {
		Users user = this.getById(currentUserId);
		return user;
	}

	@Override
	public void checkUpgrade(Long leaderId) {
		Users u = this.getById(leaderId);
		List<Users> users = this.list(new QueryWrapper<Users>().eq("first_leader", leaderId));
		List<UserLevel> levels = userLevelService.list(new QueryWrapper<UserLevel>().orderByDesc("id"));
		for (UserLevel level : levels) {
			if (level.getStraightPush() < 1) {
				continue;
			}
			if (users.size() >= level.getStraightPush()
					&& Long.valueOf(u.getLevel()).longValue() != level.getId().longValue()) {
				BigDecimal countAccount = rechargeMapper.countAccount(u.getId());
				if (null != countAccount && countAccount.compareTo(level.getAmount()) >= 0) {
					u.setLevel(level.getId().intValue());
					this.updateById(u);
					break;
				}
			}
		}
	}

	@Override
	@Transactional
	public void lock(List<Long> userIds, Integer type) {
		Collection<Users> users = this.listByIds(userIds);
		users.stream().forEach(e -> e.setIsLock(type == 1 ? 1 : 0));
		this.updateBatchById(users);
	}

	@Override
	public String downUrl(Integer sysType) {
		String downUrl = null;
		if (sysType == 1) {
			Config config = configService
					.getOne(Wrappers.<Config>lambdaQuery().eq(Config::getConfigName, "ios_link_download"));
			downUrl = config.getConfigValue();
		}
		if (sysType == 2) {
			Config config = configService
					.getOne(Wrappers.<Config>lambdaQuery().eq(Config::getConfigName, "app_wenjian"));
			downUrl = config.getConfigValue();
		}
		return downUrl;
	}

	@Override
	public HomeDto home() {
		HomeDto homeDto = new HomeDto();
		Date date = DateUtils.parseDate(DateUtils.format(new Date(), DateUtils.DEFAULT_DATE_FORMAT));
		long time = date.getTime() / 1000;

		homeDto.setPayment(userPaymentService.count(Wrappers.<UserPayment>lambdaQuery().eq(UserPayment::getStatus, 0)));
		homeDto.setUserIdentity(
				userIdentityService.count(Wrappers.<UserIdentity>lambdaQuery().eq(UserIdentity::getStatus, 0)));
		homeDto.setAppeal(pigAppealService.count(Wrappers.<PigAppeal>lambdaQuery().eq(PigAppeal::getStatus, 0)));
		homeDto.setWorkOrder(workOrderService.count(Wrappers.<WorkOrder>lambdaQuery().eq(WorkOrder::getStatus, 1)));
		homeDto.setRecharge(rechargeService.count(Wrappers.<Recharge>lambdaQuery().eq(Recharge::getPayStatus, 0)));
		// 今日新增
		String format = DateUtils.format(new Date(), DateUtils.DEFAULT_DATE_FORMAT);
		List<newUsersDto> newUsersDtos = usersMapper.newUsers(format, format);
		homeDto.setTodayAddUsers(CollectionUtils.isEmpty(newUsersDtos) ? 0 : newUsersDtos.size());
		// 今日订单
		homeDto.setTodayOrders(pigOrderService.count(
				Wrappers.<PigOrder>lambdaQuery().eq(PigOrder::getPayStatus, 2).ge(PigOrder::getEstablishTime, time)));
		// 总会员数
		homeDto.setTotalUsers(this.count());
		// 会员总资产 todo 没有用户可能有空指针
		QueryWrapper<Users> contract_wrapper = new QueryWrapper<>();
		contract_wrapper.select("sum(pay_points) as payPoints ");
		homeDto.setTotalAmount(new BigDecimal(String.valueOf(this.getMap(contract_wrapper).get("payPoints"))));
		
		QueryWrapper<Users> recom_wrapper = new QueryWrapper<>();
		recom_wrapper.select("sum(recom_income) as recomIncome ");
		homeDto.setTotalRecomIncome(new BigDecimal(String.valueOf(this.getMap(recom_wrapper).get("recomIncome"))));
		// 今日访问会员
		homeDto.setTodayVisitUsers(this.count(Wrappers.<Users>lambdaQuery().ge(Users::getLastLoginTime, time)));

		List<UserExclusivePig> allUserPigs = userExclusivePigService
				.list(Wrappers.<UserExclusivePig>lambdaQuery().eq(UserExclusivePig::getIsPigLock, 0));

		homeDto.setTotalProducts(allUserPigs.size());

		BigDecimal totalMoney = BigDecimal.ZERO;

		for (UserExclusivePig pig : allUserPigs) {
			totalMoney = totalMoney.add(pig.getPrice());
		}
		homeDto.setTotalMoney(totalMoney);
		return homeDto;
	}

	@Override
	public List<newUsersDto> newUsers(String startDate, String endDate) {
		List<newUsersDto> list = usersMapper.newUsers(startDate, endDate);
		return list;
	}

	@Override
	public UserTeamDto getTeamLevelNum(Long id) {
		UserTeamDto userTeamDto = new UserTeamDto();
		List<UserTeamLevelDto> list = usersMapper.getTeamLevelNum(id);
		if (!CollectionUtils.isEmpty(list)) {
			list.stream().forEach(e -> {
				if (e.getLevel() == 1) {
					userTeamDto.setOneLevel(e.getNumber());
				}
				if (e.getLevel() == 2) {
					userTeamDto.setTwoLevel(e.getNumber());
				}
				if (e.getLevel() == 3) {
					userTeamDto.setThreeLevel(e.getNumber());
				}
				if (e.getLevel() == 4) {
					userTeamDto.setFourLevel(e.getNumber());
				}
				if (e.getLevel() == 5) {
					userTeamDto.setFiveLevel(e.getNumber());
				}
				if (e.getLevel() == 6) {
					userTeamDto.setSixLevel(e.getNumber());
				}
			});
		}
		return userTeamDto;
	}

}