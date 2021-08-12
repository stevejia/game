package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.DistributLevelModifyDto;
import com.futures.model.dto.DistributLevelSaveDto;
import com.futures.model.entity.DistributLevel;
import com.futures.service.DistributLevelService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("distributLevel")
@Api(tags = "分销商等级管理")
public class DistributLevelController {

    @Autowired
    private DistributLevelService distributLevelService;

    @ApiOperation(value = "【分销商等级管理】列表", notes = "【分销商等级管理】列表", response = DistributLevel.class)
    @PostMapping("queryDistributLevel")
    public BaseResponse queryDistributLevel(Page page) {
        return BaseResponse.success(distributLevelService.queryDistributLevel(page));
    }

    @ApiOperation(value = "【分销商等级管理】详情", notes = "【分销商等级管理】详情", response = DistributLevel.class)
    @PostMapping("getDistributLevel")
    public BaseResponse getDistributLevel(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(distributLevelService.getById(id));
    }

    @ApiOperation(value = "【分销商等级管理】添加", notes = "【分销商等级管理】添加")
    @PostMapping("saveDistributLevel")
    @SysUserLog(module = "分销商等级管理", action = "添加")
    public BaseResponse saveDistributLevel(@Valid @ModelAttribute DistributLevelSaveDto distributLevelSaveDto) {
        distributLevelService.saveDistributLevel(distributLevelSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【分销商等级管理】修改", notes = "【分销商等级管理】修改")
    @PostMapping("modifyDistributLevel")
    @SysUserLog(module = "分销商等级管理", action = "修改")
    public BaseResponse modifyDistributLevel(@Valid @ModelAttribute DistributLevelModifyDto distributLevelModifyDto) {
        distributLevelService.modifyDistributLevel(distributLevelModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【分销商等级管理】删除", notes = "【分销商等级管理】删除")
    @PostMapping("deleteDistributLevel")
    @SysUserLog(module = "分销商等级管理", action = "删除")
    public BaseResponse deleteDistributLevel(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        distributLevelService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【分销商等级管理】分销树", notes = "【分销商等级管理】分销树")
    @PostMapping("treeList")
    @SysUserLog(module = "分销商等级管理", action = "分销树")
    public BaseResponse treeList(@ApiParam(value = "userId") Long userId, @ApiParam(value = "mobile") String mobile, @ApiParam(value = "type=oneLevel 第一级；type=anotherLevel 第二级及以后等级") String type) {
        return BaseResponse.success(distributLevelService.treeList(userId, mobile, type));
    }

}