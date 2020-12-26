package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.AccountStoreDto;
import com.gongyu.service.distribute.game.model.dto.ConfigResponseDto;
import com.gongyu.service.distribute.game.model.dto.RechargeRequestDto;
import com.gongyu.service.distribute.game.model.dto.RechargeResponseDto;
import com.gongyu.service.distribute.game.service.AccountLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("store")
@Api(tags = "会员茶籽")
public class StoreController {

    @Autowired
    private AccountLogService accountLogService;

    @ApiOperation(value = "【会员茶籽】查询总茶籽", notes = "【会员茶籽】查询总茶籽", response = AccountStoreDto.class)
    @PostMapping("queryTotalStore")
    public BaseResponse queryTotalStore() {
        return BaseResponse.success(accountLogService.queryTotalStore());
    }

    @ApiOperation(value = "【会员茶籽】茶籽流水", notes = "【会员茶籽】茶籽流水", response = AccountStoreDto.class)
    @PostMapping("queryStore")
    public BaseResponse queryStore(Page page) {
        IPage<AccountStoreDto> accountStoreDtoIPage = accountLogService.queryStore(page);
        return BaseResponse.success(accountStoreDtoIPage);
    }

    @ApiOperation(value = "【会员茶籽】充值", notes = "【会员茶籽】充值")
    @PostMapping("doRecharge")
    public BaseResponse doRecharge(@Valid @ModelAttribute RechargeRequestDto rechargeRequestDto) {
        accountLogService.doRecharge(rechargeRequestDto);
        return BaseResponse.success();
    }

    @ApiOperation(value = "【会员茶籽】充值审核反馈列表", notes = "【会员茶籽】充值审核反馈列表", response = RechargeResponseDto.class)
    @PostMapping("getRechargeList")
    public BaseResponse getRechargeList(Page page) {
        IPage<RechargeResponseDto> rechargeList = accountLogService.getRechargeList(page);
        return BaseResponse.success(rechargeList);
    }

    @ApiOperation(value = "【会员茶籽】转赠", notes = "【会员茶籽】转赠")
    @PostMapping("doReGifted")
    public BaseResponse doReGifted(@Valid @ModelAttribute RechargeRequestDto rechargeRequestDto) {
        accountLogService.doReGifted(rechargeRequestDto);
        return BaseResponse.success();
    }

    @ApiOperation(value = "【会员茶籽】查询支付方式", notes = "【会员茶籽】查询支付方式", response = ConfigResponseDto.class)
    @PostMapping("queryPayment")
    public BaseResponse queryPayment() {
        return BaseResponse.success(accountLogService.queryPayment());
    }

}