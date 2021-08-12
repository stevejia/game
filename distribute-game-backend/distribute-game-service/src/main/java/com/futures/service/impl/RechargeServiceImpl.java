package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.futures.common.enums.CommEnum;
import com.futures.common.enums.IncomeTypeEnum;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.RechargeMapper;
import com.futures.model.dto.AuthRechargeDto;
import com.futures.model.dto.RechargeDto;
import com.futures.model.dto.RechargeModifyDto;
import com.futures.model.dto.RechargeSaveDto;
import com.futures.model.entity.Recharge;
import com.futures.model.entity.Users;
import com.futures.service.AccountLogService;
import com.futures.service.RechargeService;
import com.futures.service.UsersService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RechargeServiceImpl extends CrudServiceSupport<RechargeMapper, Recharge> implements RechargeService  {

    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AccountLogService logService;

    @Override
    public IPage<RechargeDto> queryRecharge(IPage<RechargeDto> page,RechargeDto dto) {
        List<RechargeDto> list = rechargeMapper.findPage(page, dto);
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveRecharge(RechargeSaveDto rechargeSaveDto) {
        Recharge recharge = new Recharge();
        BeanUtils.copyProperties(rechargeSaveDto, recharge);
        this.save(recharge);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyRecharge(RechargeModifyDto rechargeModifyDto) {
        Recharge recharge = new Recharge();
        BeanUtils.copyProperties(rechargeModifyDto, recharge);
        recharge.setVerifierTime(DateUtil.getNowDate());
        this.updateById(recharge);
    }

    @Override
    public BaseResponse auth(AuthRechargeDto param) {
        Recharge recharge = this.getOne(new QueryWrapper<Recharge>().eq("order_id",param.getId()));
        recharge.setPayStatus(param.getStatus());
        recharge.setRemark(param.getRemark());
        recharge.setVerifierTime(DateUtil.getNowDate());
        this.updateById(recharge);
        //审核成功更新检查更新用户等级
        if(CommEnum.TRUE.getCode().intValue() == param.getStatus().intValue()){
            Long userId = recharge.getUserId();
            Users user = usersService.getById(userId);
            user.setPayPoints((int) (user.getPayPoints() + recharge.getAccount()));
            usersService.updateById(user);
            usersService.checkUpgrade(recharge.getUserId());
        }
        logService.convertAndInsert(recharge.getUserId(), BigDecimal.ZERO,BigDecimal.ZERO,recharge.getAccount().intValue(),BigDecimal.ZERO,"用户充值",IncomeTypeEnum.TOP_UP,0L,StringUtils.EMPTY,null);
        return BaseResponse.success();
    }
}