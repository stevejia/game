package com.gongyu.application.distribute.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("kline")
@Api(tags = "k线列表")
public class KLineController {

    @Autowired
    private KlineService klineService;

    @ApiOperation(value = "查询k线列表", notes = "查询k线列表", response = KlineDto.class)
    @PostMapping("querykline")
    public BaseResponse queryKLine() {
    	Rb2110KlineExample param = new Rb2110KlineExample();
    	param.createCriteria().andPeriodEqualTo(60);
    
    	List<KlineDto> kLines = klineService.queryRbKLine(param);
        return BaseResponse.success(kLines);
    }
}