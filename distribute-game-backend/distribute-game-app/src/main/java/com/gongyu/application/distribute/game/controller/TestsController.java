package com.gongyu.application.distribute.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.model.dto.HomeResultDto;
import com.gongyu.service.distribute.game.service.IncomeService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 14:04
 */
@RestController
@Api(tags = "测试")
@RequestMapping
public class TestsController {

    @Autowired
    private IncomeService incomeService;

    @ApiOperation(value = "测试计算收益",response = HomeResultDto.class)
    @PostMapping("testProcessIncome")
    public BaseResponse testProcessIncome(){
        incomeService.processIncome();
        return BaseResponse.success();
    }
}
