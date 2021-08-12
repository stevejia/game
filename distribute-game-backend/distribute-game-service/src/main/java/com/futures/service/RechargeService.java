package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.AuthRechargeDto;
import com.futures.model.dto.RechargeDto;
import com.futures.model.dto.RechargeModifyDto;
import com.futures.model.dto.RechargeSaveDto;
import com.futures.model.entity.Recharge;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import javax.validation.Valid;

public interface RechargeService extends CrudService<Recharge>{

    IPage<RechargeDto> queryRecharge(IPage<RechargeDto> page,RechargeDto dto);

    void saveRecharge(RechargeSaveDto rechargeSaveDto);

    void modifyRecharge(RechargeModifyDto rechargeModifyDto);

    BaseResponse auth(AuthRechargeDto param);
}