package com.gongyu.application.distribute.game.controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.model.dto.HomeResultDto;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.service.distribute.game.service.IncomeService;
import com.gongyu.service.distribute.game.service.PigReservationService;
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
@RequestMapping("tests")
public class TestsController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private PigReservationService pigReservationService;
    
    @ApiOperation(value = "测试计算收益",response = HomeResultDto.class)
    @PostMapping("testProcessIncome")
    public BaseResponse testProcessIncome(){
        incomeService.processIncome();
        return BaseResponse.success();
    }
    
    @ApiOperation(value = "测试",response = HomeResultDto.class)
    @PostMapping("test")
    public BaseResponse test(){
        List<PigReservation> testList = new LinkedList<PigReservation>();
        for(int i=0; i<5; i++) {
        	PigReservation  reser = new PigReservation();
        	reser.setId(Long.valueOf(i+1));
        	reser.setUserId(2256L);
        	reser.setPigId(22L);
        	reser.setReservationScene(1086L);
        	reser.setPayPoints(5);
        	testList.add(reser);
        }
        
        Set<Long> users = new HashSet<Long>();
        users.add(2256L);
        List<Long> luckUsers = new LinkedList<Long>();
        luckUsers.add(2256L);
        pigReservationService.luckStatus(users, luckUsers, testList);
//        testList.add(Long.valueOf(3));
//        testList.add(Long.valueOf(4));
//        testList.add(Long.valueOf(5));
//        testList.add(Long.valueOf(6));
//        testList.add(Long.valueOf(8));
//        testList.add(Long.valueOf(7));
//        testList.add(Long.valueOf(8));
//        boolean exist = testList.contains(3L);
        return BaseResponse.success();
    }
}
