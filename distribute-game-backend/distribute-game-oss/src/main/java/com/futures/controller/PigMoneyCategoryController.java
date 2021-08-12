package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.PigMoneyCategoryModifyDto;
import com.futures.model.dto.PigMoneyCategorySaveDto;
import com.futures.model.entity.PigMoneyCategory;
import com.futures.service.PigMoneyCategoryService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigMoneyCategory")
@Api(tags = "商品价值区间管理")
public class PigMoneyCategoryController {

    @Autowired
    private PigMoneyCategoryService pigMoneyCategoryService;

    @ApiOperation(value = "【商品价值区间管理】列表", notes = "【商品价值区间管理】列表", response = PigMoneyCategory.class)
    @PostMapping("queryPigMoneyCategory")
    public BaseResponse queryPigMoneyCategory(Page page) {
        return BaseResponse.success(pigMoneyCategoryService.queryPigMoneyCategory(page));
    }

    @ApiOperation(value = "【商品价值区间管理】详情", notes = "【商品价值区间管理】详情", response = PigMoneyCategory.class)
    @PostMapping("getPigMoneyCategory")
    public BaseResponse getPigMoneyCategory(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigMoneyCategoryService.getById(id));
    }

    @ApiOperation(value = "【商品价值区间管理】添加", notes = "【商品价值区间管理】添加")
    @PostMapping("savePigMoneyCategory")
    @SysUserLog(module = "商品价值区间管理", action = "添加")
    public BaseResponse savePigMoneyCategory(@Valid @ModelAttribute PigMoneyCategorySaveDto pigMoneyCategorySaveDto) {
        pigMoneyCategoryService.savePigMoneyCategory(pigMoneyCategorySaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【商品价值区间管理】修改", notes = "【商品价值区间管理】修改")
    @PostMapping("modifyPigMoneyCategory")
    @SysUserLog(module = "商品价值区间管理", action = "修改")
    public BaseResponse modifyPigMoneyCategory(@Valid @ModelAttribute PigMoneyCategoryModifyDto pigMoneyCategoryModifyDto) {
        pigMoneyCategoryService.modifyPigMoneyCategory(pigMoneyCategoryModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【商品价值区间管理】删除", notes = "【商品价值区间管理】删除")
    @PostMapping("deletePigMoneyCategory")
    @SysUserLog(module = "商品价值区间管理", action = "删除")
    public BaseResponse deletePigMoneyCategory(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigMoneyCategoryService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}