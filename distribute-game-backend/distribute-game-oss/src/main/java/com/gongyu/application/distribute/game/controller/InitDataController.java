package com.gongyu.application.distribute.game.controller;

import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.service.PigGoodsService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/2 20:47
 */
@RestController
@RequestMapping("init")
@Api(tags = "初始化前端基础数据")
public class InitDataController {

    @Autowired
    private PigGoodsService goodsService;

    @ApiOperation(value = "精灵列表",response = PigGoods.class)
    @PostMapping(value = "initGoodsList")
    public BaseResponse initGoodsList(){
        return BaseResponse.success(goodsService.list());
    }
}
