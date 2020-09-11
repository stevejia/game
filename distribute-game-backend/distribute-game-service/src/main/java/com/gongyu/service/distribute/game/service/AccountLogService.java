package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.AccountLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.math.BigDecimal;
import java.util.List;

public interface AccountLogService extends CrudService<AccountLog> {

    IPage<AccountLog> queryAccountLog(IPage<AccountLog> page, Long userId, String mobile);

    void saveAccountLog(AccountLogSaveDto accountLogSaveDto);

    void modifyAccountLog(AccountLogModifyDto accountLogModifyDto);

    void convertAndInsert(Long userId, BigDecimal money, BigDecimal frozenMoney, Integer payPoints, BigDecimal contractReve, String desc, IncomeTypeEnum typeEnum, Long pigId,String orderNo,Long giverUserId);


    List<AccountLogDto> findPage(IPage<AccountLog> page, Long userId, String type);

    IPage<AccountStoreDto> queryStore(Page page);

    AccountStoreDto queryTotalStore();

    void doRecharge(RechargeRequestDto rechargeRequestDto);

    void doReGifted(RechargeRequestDto rechargeRequestDto);

    IPage<RechargeResponseDto> getRechargeList(Page page);

    List<ConfigResponseDto> queryPayment();

    void giveAccountForRegister(Long userId);
}