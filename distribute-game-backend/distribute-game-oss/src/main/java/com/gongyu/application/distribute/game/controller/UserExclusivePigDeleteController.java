package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.DestoryPigReqDto;
import com.gongyu.service.distribute.game.model.dto.PigDeletePageReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDeleteModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDeleteSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDelete;
import com.gongyu.service.distribute.game.service.UserExclusivePigDeleteService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userExclusivePigDelete")
@Api(tags = "销毁精灵")
public class UserExclusivePigDeleteController {

    @Autowired
    private UserExclusivePigDeleteService userExclusivePigDeleteService;

    @ApiOperation(value = "【销毁精灵】列表", notes = "【销毁精灵】列表", response = UserExclusivePigDelete.class)
    @PostMapping("queryUserExclusivePigDelete")
    public BaseResponse queryUserExclusivePigDelete(Page page, PigDeletePageReqDto param) {
        return BaseResponse.success(userExclusivePigDeleteService.queryUserExclusivePigDelete(page,param));
    }

    @ApiOperation(value = "【销毁精灵】详情", notes = "【销毁精灵】详情", response = UserExclusivePigDelete.class)
    @PostMapping("getUserExclusivePigDelete")
    public BaseResponse getUserExclusivePigDelete(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userExclusivePigDeleteService.getById(id));
    }

    @ApiOperation(value = "【销毁精灵】添加", notes = "【销毁精灵】添加")
    @PostMapping("saveUserExclusivePigDelete")
    @SysUserLog(module = "销毁精灵", action = "添加")
    public BaseResponse saveUserExclusivePigDelete(@Valid @ModelAttribute UserExclusivePigDeleteSaveDto userExclusivePigDeleteSaveDto) {
        userExclusivePigDeleteService.saveUserExclusivePigDelete(userExclusivePigDeleteSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【销毁精灵】修改", notes = "【销毁精灵】修改")
    @PostMapping("modifyUserExclusivePigDelete")
    @SysUserLog(module = "销毁精灵", action = "修改")
    public BaseResponse modifyUserExclusivePigDelete(@Valid @ModelAttribute UserExclusivePigDeleteModifyDto userExclusivePigDeleteModifyDto) {
        userExclusivePigDeleteService.modifyUserExclusivePigDelete(userExclusivePigDeleteModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【销毁精灵】删除", notes = "【销毁精灵】删除")
    @PostMapping("deleteUserExclusivePigDelete")
    @SysUserLog(module = "销毁精灵", action = "删除")
    public BaseResponse deleteUserExclusivePigDelete(@ApiParam(value = "精灵实例ID", required = true) @RequestParam()Long id) {
        userExclusivePigDeleteService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【销毁精灵】销毁精灵", notes = "【销毁精灵】销毁精灵")
    @PostMapping("destoryPig")
    @SysUserLog(module = "销毁精灵", action = "删除")
    public BaseResponse destoryPig(DestoryPigReqDto param) {
        return userExclusivePigDeleteService.destoryPig(param);
    }
}