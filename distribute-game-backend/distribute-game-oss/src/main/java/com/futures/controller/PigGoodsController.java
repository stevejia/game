package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.PigGoodsModifyDto;
import com.futures.model.dto.PigGoodsSaveDto;
import com.futures.model.dto.TodaySalePigSummaryReqDto;
import com.futures.model.entity.PigGoods;
import com.futures.service.PigGoodsService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigGoods")
@Api(tags = "商品管理")
public class PigGoodsController {

    @Autowired
    private PigGoodsService pigGoodsService;

    @ApiOperation(value = "【商品管理】列表", notes = "【商品管理】列表", response = PigGoods.class)
    @PostMapping("queryPigGoods")
    public BaseResponse queryPigGoods(Page page) {
        return BaseResponse.success(pigGoodsService.queryPigGoods(page));
    }
    @ApiOperation(value = "【商品管理】详情", notes = "【商品管理】详情", response = PigGoods.class)
    @PostMapping("queryTodaySalePigSummary")
    public BaseResponse queryTodaySalePigSummary(TodaySalePigSummaryReqDto param) {
    	return BaseResponse.success(pigGoodsService.queryTodaySalePigSummary(param));
    }
    
    @ApiOperation(value = "【商品管理】详情", notes = "【商品管理】详情", response = PigGoods.class)
    @PostMapping("getPigGoods")
    public BaseResponse getPigGoods(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigGoodsService.getById(id));
    }

    @ApiOperation(value = "【商品管理】添加", notes = "【商品管理】添加")
    @PostMapping("savePigGoods")
    @SysUserLog(module = "商品管理", action = "添加")
    public BaseResponse savePigGoods(@Valid PigGoodsSaveDto pigGoodsSaveDto) {
        pigGoodsService.savePigGoods(pigGoodsSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【商品管理】修改", notes = "【商品管理】修改")
    @PostMapping("modifyPigGoods")
    @SysUserLog(module = "商品管理", action = "修改")
    public BaseResponse modifyPigGoods(@Valid @ModelAttribute PigGoodsModifyDto pigGoodsModifyDto) {
        pigGoodsService.modifyPigGoods(pigGoodsModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【商品管理】删除", notes = "【商品管理】删除")
    @PostMapping("deletePigGoods")
    @SysUserLog(module = "商品管理", action = "删除")
    public BaseResponse deletePigGoods(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigGoodsService.removeById(id);
        return BaseResponse.success("删除成功");
    }


}