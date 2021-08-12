package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.PigGoodsSpellLogModifyDto;
import com.futures.model.dto.PigGoodsSpellLogSaveDto;
import com.futures.model.entity.PigGoodsSpellLog;
import com.futures.service.PigGoodsSpellLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigGoodsSpellLog")
@Api(tags = "商品合成日志管理")
public class PigGoodsSpellLogController {

    @Autowired
    private PigGoodsSpellLogService pigGoodsSpellLogService;

    @ApiOperation(value = "【商品合成日志管理】列表", notes = "【商品合成日志管理】列表", response = PigGoodsSpellLog.class)
    @PostMapping("queryPigGoodsSpellLog")
    public BaseResponse queryPigGoodsSpellLog(Page page) {
        return BaseResponse.success(pigGoodsSpellLogService.queryPigGoodsSpellLog(page));
    }

    @ApiOperation(value = "【商品合成日志管理】详情", notes = "【商品合成日志管理】详情", response = PigGoodsSpellLog.class)
    @PostMapping("getPigGoodsSpellLog")
    public BaseResponse getPigGoodsSpellLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigGoodsSpellLogService.getById(id));
    }

    @ApiOperation(value = "【商品合成日志管理】添加", notes = "【商品合成日志管理】添加")
    @PostMapping("savePigGoodsSpellLog")
    @SysUserLog(module = "商品合成日志管理", action = "添加")
    public BaseResponse savePigGoodsSpellLog(@Valid @ModelAttribute PigGoodsSpellLogSaveDto pigGoodsSpellLogSaveDto) {
        pigGoodsSpellLogService.savePigGoodsSpellLog(pigGoodsSpellLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【商品合成日志管理】修改", notes = "【商品合成日志管理】修改")
    @PostMapping("modifyPigGoodsSpellLog")
    @SysUserLog(module = "商品合成日志管理", action = "修改")
    public BaseResponse modifyPigGoodsSpellLog(@Valid @ModelAttribute PigGoodsSpellLogModifyDto pigGoodsSpellLogModifyDto) {
        pigGoodsSpellLogService.modifyPigGoodsSpellLog(pigGoodsSpellLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【商品合成日志管理】删除", notes = "【商品合成日志管理】删除")
    @PostMapping("deletePigGoodsSpellLog")
    @SysUserLog(module = "商品合成日志管理", action = "删除")
    public BaseResponse deletePigGoodsSpellLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigGoodsSpellLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}