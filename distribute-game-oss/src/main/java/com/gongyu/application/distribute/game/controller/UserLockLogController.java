package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.UserLockLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLockLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLockLog;
import com.gongyu.service.distribute.game.service.UserLockLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userLockLog")
@Api(tags = "用户封号记管理")
public class UserLockLogController {

    @Autowired
    private UserLockLogService userLockLogService;

    @ApiOperation(value = "【用户封号记管理】列表", notes = "【用户封号记管理】列表", response = UserLockLog.class)
    @PostMapping("queryUserLockLog")
    public BaseResponse queryUserLockLog(Page page) {
        return BaseResponse.success(userLockLogService.queryUserLockLog(page));
    }

    @ApiOperation(value = "【用户封号记管理】详情", notes = "【用户封号记管理】详情", response = UserLockLog.class)
    @PostMapping("getUserLockLog")
    public BaseResponse getUserLockLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userLockLogService.getById(id));
    }

    @ApiOperation(value = "【用户封号记管理】添加", notes = "【用户封号记管理】添加")
    @PostMapping("saveUserLockLog")
    @SysUserLog(module = "用户封号记管理", action = "添加")
    public BaseResponse saveUserLockLog(@Valid @ModelAttribute UserLockLogSaveDto userLockLogSaveDto) {
        userLockLogService.saveUserLockLog(userLockLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户封号记管理】修改", notes = "【用户封号记管理】修改")
    @PostMapping("modifyUserLockLog")
    @SysUserLog(module = "用户封号记管理", action = "修改")
    public BaseResponse modifyUserLockLog(@Valid @ModelAttribute UserLockLogModifyDto userLockLogModifyDto) {
        userLockLogService.modifyUserLockLog(userLockLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户封号记管理】删除", notes = "【用户封号记管理】删除")
    @PostMapping("deleteUserLockLog")
    @SysUserLog(module = "用户封号记管理", action = "删除")
    public BaseResponse deleteUserLockLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userLockLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}