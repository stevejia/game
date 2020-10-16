package com.gongyu.application.distribute.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.service.IncomeService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sysoperation")
@Api(tags = "系统操作")
public class SystemOperationController {

    @Autowired
    private IncomeService incomeService;

    @ApiOperation(value = "手动计算收益", notes = "手动计算收益")
    @PostMapping("processIncome")
    public BaseResponse processIncome() {
    	incomeService.processIncome();
        return BaseResponse.success();
    }
}