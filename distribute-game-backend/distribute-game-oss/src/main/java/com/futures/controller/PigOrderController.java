package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.PigOrderModifyDto;
import com.futures.model.dto.PigOrderSaveDto;
import com.futures.model.entity.PigOrder;
import com.futures.service.PigOrderService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigOrder")
@Api(tags = "交易管理")
public class PigOrderController {

    @Autowired
    private PigOrderService pigOrderService;

    @ApiOperation(value = "【交易管理】列表", notes = "【交易管理】列表", response = PigOrder.class)
    @PostMapping("queryPigOrder")
    public BaseResponse queryPigOrder(Page page,PigOrderModifyDto dto) {
        return BaseResponse.success(pigOrderService.queryPigOrder(page,dto));
    }

    @ApiOperation(value = "【交易管理】详情", notes = "【交易管理】详情", response = PigOrder.class)
    @PostMapping("getPigOrder")
    public BaseResponse getPigOrder(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigOrderService.getById(id));
    }

    @ApiOperation(value = "【交易管理】添加", notes = "【交易管理】添加")
    @PostMapping("savePigOrder")
    @SysUserLog(module = "交易管理", action = "添加")
    public BaseResponse savePigOrder(@Valid @ModelAttribute PigOrderSaveDto pigOrderSaveDto) {
        pigOrderService.savePigOrder(pigOrderSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【交易管理】修改", notes = "【交易管理】修改")
    @PostMapping("modifyPigOrder")
    @SysUserLog(module = "交易管理", action = "修改")
    public BaseResponse modifyPigOrder(@Valid @ModelAttribute PigOrderModifyDto pigOrderModifyDto) {
        pigOrderService.modifyPigOrder(pigOrderModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【交易管理】删除", notes = "【交易管理】删除")
    @PostMapping("deletePigOrder")
    @SysUserLog(module = "交易管理", action = "删除")
    public BaseResponse deletePigOrder(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigOrderService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}