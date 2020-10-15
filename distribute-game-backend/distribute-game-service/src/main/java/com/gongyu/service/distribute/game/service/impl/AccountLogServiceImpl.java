package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.AccountLogMapper;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.AccountLog;
import com.gongyu.service.distribute.game.model.entity.Config;
import com.gongyu.service.distribute.game.model.entity.Recharge;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.AccountLogService;
import com.gongyu.service.distribute.game.service.RechargeService;
import com.gongyu.service.distribute.game.service.UsersService;
import com.gongyu.service.distribute.game.utils.DateUtils;
import com.gongyu.service.distribute.game.utils.Ids;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.util.MD5;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountLogServiceImpl extends CrudServiceSupport<AccountLogMapper, AccountLog> implements AccountLogService {

    @Autowired
    private AccountLogMapper accountLogMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private ConfigServiceImpl configService;
    @Autowired
    private AccountLogService accountLogService;

    @Override
    public IPage<AccountLog> queryAccountLog(IPage<AccountLog> page, Long userId, String mobile) {
        LambdaQueryWrapper<AccountLog> accountLogLambdaQueryWrapper = Wrappers.lambdaQuery();
        if (userId != null) {
            accountLogLambdaQueryWrapper.eq(AccountLog::getUserId, userId);
        }
        if (StringUtils.isNotEmpty(mobile)) {
            Users one = usersService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, mobile));
            if (one != null) {
                accountLogLambdaQueryWrapper.eq(AccountLog::getUserId, one.getId());
            }
        }
        IPage<AccountLog> page1 = this.page(page, accountLogLambdaQueryWrapper);
        List<AccountLog> records = page1.getRecords();
        records.stream().forEach(e -> {
            e.setChangeTime(e.getChangeTime() * 1000);
            Users byId = usersService.getById(e.getUserId());
            if(17 == e.getType()){
                e.setRecomIncome(e.getContractRevenue());
                e.setContractRevenue(BigDecimal.ZERO);
            }
            e.setMobile(byId.getMobile());
        });
        return page1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveAccountLog(AccountLogSaveDto accountLogSaveDto) {
        AccountLog accountLog = new AccountLog();
        BeanUtils.copyProperties(accountLogSaveDto, accountLog);
        accountLog.setDescs(accountLogSaveDto.getDesc());
        this.save(accountLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyAccountLog(AccountLogModifyDto accountLogModifyDto) {
        AccountLog accountLog = new AccountLog();
        BeanUtils.copyProperties(accountLogModifyDto, accountLog);
        this.updateById(accountLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void convertAndInsert(Long userId, BigDecimal money, BigDecimal frozenMoney, Integer payPoints, BigDecimal contractReve, String desc, IncomeTypeEnum typeEnum, Long pigId,String orderNo,Long giverUserId) {
        AccountLog accountLog = new AccountLog();
        accountLog.setUserId(userId);
        accountLog.setUserMoney(money);
        accountLog.setFrozenMoney(frozenMoney);
        accountLog.setPayPoints(payPoints);
        accountLog.setContractRevenue(contractReve);
        accountLog.setChangeTime(DateUtil.getNowDate());
        accountLog.setDescs(desc);
        accountLog.setType(typeEnum.getCode());
        accountLog.setPigId(pigId);
        accountLog.setOrderSn(StringUtils.isNotEmpty(orderNo) ? orderNo : "");
        if(null != giverUserId){
            accountLog.setGiverUserId(giverUserId);
        }
        this.save(accountLog);
    }

    @Override
    public List<AccountLogDto> findPage(IPage<AccountLog> page, Long userId, String type) {
        List<AccountLogDto> dtos = new ArrayList<>();
        List<AccountLog> list = accountLogMapper.findPage(page, userId, type);
        list.forEach(e -> {
            AccountLogDto dto = new AccountLogDto();
            BeanUtils.copyProperties(e, dto);
            if (12 == e.getType().intValue()) {
                dto.setIncomTypeName("团队奖产生收益");
            }
            if (13 == e.getType().intValue()) {
                dto.setIncomTypeName("推荐收益");
            }
            if (9 == e.getType().intValue()){
                dto.setIncomTypeName("出售推广收益");
            }
            if (17 == e.getType().intValue()){
                dto.setIncomTypeName("推广收益");
            }
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public IPage<AccountStoreDto> queryStore(Page page) {
        AccountStoreDto accountStoreDto = new AccountStoreDto();
        List<AccountStoreDetailDto> storeList = new ArrayList<>();
        Long userId = WebUtils.getCurrentUserId();
        Users byId = usersService.getById(userId);
        accountStoreDto.setTotalStore(String.valueOf(byId == null ? 0 : byId.getPayPoints()));
        List<AccountLog> list = accountLogMapper.findPage(page, userId, null);
        list.forEach(e -> {
            String type = "1,2,3,4,5,6,18";
            if(type.startsWith(e.getType()+",")||
                    type.contains(","+e.getType()+",")||
                    type.endsWith(","+e.getType())){
                AccountStoreDetailDto accountStoreDetailDto = new AccountStoreDetailDto();
                accountStoreDetailDto.setDescribe(e.getDescs());
                accountStoreDetailDto.setChangeTime(DateUtils.getDateFormatToString(DateUtils.getDate(e.getChangeTime() * 1000), DateUtils.DATETIME_DEFAULT_FORMAT));
                accountStoreDetailDto.setStore(String.valueOf(e.getPayPoints()));
                if(null != e.getGiverUserId()){
                    Users users = usersService.getById(e.getGiverUserId());
                    accountStoreDetailDto.setGiverUserPhone(users.getMobile());
                }
                storeList.add(accountStoreDetailDto);
            }

        });
        page.setRecords(storeList);
        return page;
    }

    @Override
    public AccountStoreDto queryTotalStore() {
        AccountStoreDto accountStoreDto = new AccountStoreDto();
        Users byId = usersService.getById(WebUtils.getCurrentUserId());
        accountStoreDto.setTotalStore(String.valueOf(byId == null ? 0 : byId.getPayPoints()));
        return accountStoreDto;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void doRecharge(RechargeRequestDto rechargeRequestDto) {
        if (StringUtils.isEmpty(rechargeRequestDto.getImgUrl())) {
            throw new BizException("请上传支付凭证");
        }
        Users byId = usersService.getById(WebUtils.getCurrentUserId());
        if (byId == null) {
            throw new BizException("当前用户不存在");
        }
        if (!MD5.getMD5Code(rechargeRequestDto.getPaypwd()).equals(byId.getPaypwd())) {
            throw new BizException("请输入正确的交易密码");
        }
        //  保存充值记录
        Recharge recharge = new Recharge();
        recharge.setImgUrl(rechargeRequestDto.getImgUrl());
        recharge.setPayStatus(0);
        recharge.setAccount(rechargeRequestDto.getAccount());
        recharge.setAddTime(System.currentTimeMillis() / 1000);
        recharge.setOrderSn(Ids.uuid32());
        recharge.setNickname(byId.getNickname());
        recharge.setUserId(byId.getId());
        rechargeService.save(recharge);
    }

    @Transactional
    @Override
    public void doReGifted(RechargeRequestDto rechargeRequestDto) {
        Users byId = usersService.getById(WebUtils.getCurrentUserId());
        if (byId == null) {
            throw new BizException("当前用户不存在");
        }
        if (!MD5.getMD5Code(rechargeRequestDto.getPaypwd()).equals(byId.getPaypwd())) {
            throw new BizException("请输入正确的交易密码");
        }
        Users users = usersService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getCode, rechargeRequestDto.getCode()));
        if (users == null) {
            throw new BizException("转赠对象用户不存在");
        }
        if(byId.getPayPoints() < rechargeRequestDto.getAccount() + 100){
            throw new BizException("转赠后的积分不能低于100");
        }
        //检查转增积分数量是否合法
        Config lowest = configService.getOne(new QueryWrapper<Config>().eq("config_name", "transferring"));
        if(Float.valueOf(lowest.getConfigValue()).floatValue() > rechargeRequestDto.getAccount().floatValue()){
            throw new BizException("转增积分不得低于" + lowest.getConfigValue());
        }
        Config ruleNum = configService.getOne(new QueryWrapper<Config>().eq("config_name", "transfer_multiple"));
        if(Math.round(rechargeRequestDto.getAccount()) % Long.valueOf(ruleNum.getConfigValue()) != 0){
            throw new BizException("转增金额需要是" + ruleNum.getConfigValue() + "的倍数");
        }
        byId.setPayPoints(byId.getPayPoints() - Math.round(rechargeRequestDto.getAccount()));
        users.setPayPoints(users.getPayPoints() + Math.round(rechargeRequestDto.getAccount()));
        usersService.updateById(byId);
        usersService.updateById(users);
        accountLogService.convertAndInsert(byId.getId(), new BigDecimal("0"), new BigDecimal("0"), Math.round(rechargeRequestDto.getAccount()) * -1, new BigDecimal("0"), IncomeTypeEnum.GIVE_AWAY.getDesc(), IncomeTypeEnum.GIVE_AWAY, 0L,StringUtils.EMPTY,users.getId());
        accountLogService.convertAndInsert(users.getId(), new BigDecimal("0"), new BigDecimal("0"), Math.round(rechargeRequestDto.getAccount()), new BigDecimal("0"), IncomeTypeEnum.GIVE_AWAY.getDesc(), IncomeTypeEnum.GIVE_AWAY, 0L,StringUtils.EMPTY,byId.getId());
    }

    @Override
    public Page<RechargeResponseDto> getRechargeList(Page page) {
        List<RechargeResponseDto> list = new ArrayList<>();
        RechargeDto dto = new RechargeDto();
        dto.setUserId(WebUtils.getCurrentUserId());
        IPage iPage = rechargeService.queryRecharge(page, dto);
        List<RechargeDto> records = iPage.getRecords();
        records.stream().forEach(e -> {
            RechargeResponseDto rechargeResponseDto = new RechargeResponseDto();
            rechargeResponseDto.setAccount(e.getAccount());
            rechargeResponseDto.setImgUrl(e.getImgUrl());
            rechargeResponseDto.setPayStatus(e.getPayStatus());
            rechargeResponseDto.setRemark(e.getRemark());
            rechargeResponseDto.setAddTime(DateUtils.getDateFormatToString(DateUtils.getDate(e.getAddTime() * 1000), DateUtils.DATETIME_DEFAULT_FORMAT));
            list.add(rechargeResponseDto);
        });
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ConfigResponseDto> queryPayment() {
        Config wx = configService.getOne(new QueryWrapper<Config>().eq("config_name", "recharge_wx_qrcode"));
        Config wxDesc = configService.getOne(new QueryWrapper<Config>().eq("config_name", "recharge_wx_desc"));
        Config ali = configService.getOne(new QueryWrapper<Config>().eq("config_name", "recharge_ali_qrcode"));
        Config aliDesc = configService.getOne(new QueryWrapper<Config>().eq("config_name", "recharge_ali_desc"));
        List<Config> configs = new ArrayList<>();
        configs.add(wx);
        configs.add(wxDesc);
        configs.add(ali);
        configs.add(aliDesc);
        List<ConfigResponseDto> configResponseDtos = new ArrayList<>();
        configs.stream()
                .filter(item -> null != item)
                .forEach(e -> {
                    ConfigResponseDto configResponseDto = new ConfigResponseDto();
                    configResponseDto.setConfigName(e.getConfigName());
                    configResponseDto.setRemark(e.getRemark());
                    configResponseDto.setTitle(e.getTitle());
                    configResponseDto.setConfigValue(e.getConfigValue());
                    configResponseDtos.add(configResponseDto);
                });
        return configResponseDtos;
    }

    @Override
    public void giveAccountForRegister(Long userId) {

        Config lowest = configService.getOne(new QueryWrapper<Config>().eq("config_name", "user_registry_paypoint"));
        if(Float.valueOf(lowest.getConfigValue()).floatValue() <=0){
            return;
        }
        Users user =  usersService.getById(userId);
        user.setPayPoints(Float.valueOf(lowest.getConfigValue()).intValue());
        usersService.updateById(user);
        AccountLog log = new AccountLog();
        log.setUserId(userId);
        log.setPayPoints(user.getPayPoints());
        log.setChangeTime(DateUtil.getNowDate());
        log.setType(IncomeTypeEnum.REGIST.getCode());
        log.setFrozenMoney(new BigDecimal(0));
        log.setContractRevenue(new BigDecimal(0));
        log.setOrderId(userId.intValue());
        log.setOrderSn(userId.toString());
        accountLogService.save(log);

    }
}