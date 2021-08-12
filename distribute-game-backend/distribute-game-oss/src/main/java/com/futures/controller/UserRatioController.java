package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.UserRatioModifyDto;
import com.futures.model.dto.UserRatioSaveDto;
import com.futures.model.entity.UserRatio;
import com.futures.service.UserRatioService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userRatio")
@Api(tags = "月分红设置管理")
public class UserRatioController {

    @Autowired
    private UserRatioService userRatioService;

    @ApiOperation(value = "【月分红设置管理】列表", notes = "【月分红设置管理】列表", response = UserRatio.class)
    @PostMapping("queryUserRatio")
    public BaseResponse queryUserRatio(Page page) {
        return BaseResponse.success(userRatioService.queryUserRatio(page));
    }

    @ApiOperation(value = "【月分红设置管理】详情", notes = "【月分红设置管理】详情", response = UserRatio.class)
    @PostMapping("getUserRatio")
    public BaseResponse getUserRatio(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userRatioService.getById(id));
    }

    @ApiOperation(value = "【月分红设置管理】添加", notes = "【月分红设置管理】添加")
    @PostMapping("saveUserRatio")
    @SysUserLog(module = "月分红设置管理", action = "添加")
    public BaseResponse saveUserRatio(@Valid @ModelAttribute UserRatioSaveDto userRatioSaveDto) {
        userRatioService.saveUserRatio(userRatioSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【月分红设置管理】修改", notes = "【月分红设置管理】修改")
    @PostMapping("modifyUserRatio")
    @SysUserLog(module = "月分红设置管理", action = "修改")
    public BaseResponse modifyUserRatio(@Valid @ModelAttribute UserRatioModifyDto userRatioModifyDto) {
        userRatioService.modifyUserRatio(userRatioModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【月分红设置管理】删除", notes = "【月分红设置管理】删除")
    @PostMapping("deleteUserRatio")
    @SysUserLog(module = "月分红设置管理", action = "删除")
    public BaseResponse deleteUserRatio(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userRatioService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}