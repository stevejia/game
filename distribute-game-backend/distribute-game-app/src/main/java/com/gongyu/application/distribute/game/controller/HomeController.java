package com.gongyu.application.distribute.game.controller;

import com.gongyu.service.distribute.game.model.dto.HomeResultDto;
import com.gongyu.service.distribute.game.service.PigGoodsService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 14:04
 */
@RestController
@Api(tags = "首页")
@RequestMapping
public class HomeController {

    @Autowired
    private PigGoodsService goodsService;

    @ApiOperation(value = "首页数据",response = HomeResultDto.class)
    @PostMapping("home")
    public BaseResponse home(){
        return goodsService.home();
    }

    @ApiOperation(value = "清除缓存",response = HomeResultDto.class)
    @PostMapping("reHome")
    public BaseResponse reHome(@ApiParam(value = "缓存key[首页：home_goods、home_article]") String key){
        RedisUtils.set("home",null);
        if(StringUtils.isNotBlank(key)){
            RedisUtils.remove(key);
        }
        return BaseResponse.success();
    }

    @ApiOperation(value = "调试获取",response = HomeResultDto.class)
    @PostMapping("redisGet")
    public BaseResponse redisGet(@ApiParam(value = "缓存key[调试获取]") String key){
        Object o = null;
        if(StringUtils.isNotBlank(key)){
           o = RedisUtils.get(key);
        }
        return BaseResponse.success(o);
    }

}
