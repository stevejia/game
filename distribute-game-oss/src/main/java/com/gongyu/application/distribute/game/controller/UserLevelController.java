package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.UserLevelModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLevelSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLevel;
import com.gongyu.service.distribute.game.service.UserLevelService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userLevel")
@Api(tags = "用户等级管理")
public class UserLevelController {

    @Autowired
    private UserLevelService userLevelService;

    @ApiOperation(value = "【用户等级管理】列表", notes = "【用户等级管理】列表", response = UserLevel.class)
    @PostMapping("queryUserLevel")
    public BaseResponse queryUserLevel(Page page) {
        return BaseResponse.success(userLevelService.queryUserLevel(page));
    }

    @ApiOperation(value = "【用户等级管理】详情", notes = "【用户等级管理】详情", response = UserLevel.class)
    @PostMapping("getUserLevel")
    public BaseResponse getUserLevel(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userLevelService.getById(id));
    }

    @ApiOperation(value = "【用户等级管理】添加", notes = "【用户等级管理】添加")
    @PostMapping("saveUserLevel")
    @SysUserLog(module = "用户等级管理", action = "添加")
    public BaseResponse saveUserLevel(@Valid @RequestBody UserLevelSaveDto userLevelSaveDto) {
        userLevelService.saveUserLevel(userLevelSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户等级管理】修改", notes = "【用户等级管理】修改")
    @PostMapping("modifyUserLevel")
    @SysUserLog(module = "用户等级管理", action = "修改")
    public BaseResponse modifyUserLevel(@Valid @RequestBody UserLevelModifyDto userLevelModifyDto) {
        userLevelService.modifyUserLevel(userLevelModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户等级管理】删除", notes = "【用户等级管理】删除")
    @PostMapping("deleteUserLevel")
    @SysUserLog(module = "用户等级管理", action = "删除")
    public BaseResponse deleteUserLevel(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userLevelService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}