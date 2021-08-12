package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.UserMessageModifyDto;
import com.futures.model.dto.UserMessageSaveDto;
import com.futures.model.entity.UserMessage;
import com.futures.service.UserMessageService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userMessage")
@Api(tags = "用户消息管理")
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    @ApiOperation(value = "【用户消息管理】列表", notes = "【用户消息管理】列表", response = UserMessage.class)
    @PostMapping("queryUserMessage")
    public BaseResponse queryUserMessage(Page page) {
        return BaseResponse.success(userMessageService.queryUserMessage(page));
    }

    @ApiOperation(value = "【用户消息管理】详情", notes = "【用户消息管理】详情", response = UserMessage.class)
    @PostMapping("getUserMessage")
    public BaseResponse getUserMessage(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userMessageService.getById(id));
    }

    @ApiOperation(value = "【用户消息管理】添加", notes = "【用户消息管理】添加")
    @PostMapping("saveUserMessage")
    @SysUserLog(module = "用户消息管理", action = "添加")
    public BaseResponse saveUserMessage(@Valid @ModelAttribute UserMessageSaveDto userMessageSaveDto) {
        userMessageService.saveUserMessage(userMessageSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户消息管理】修改", notes = "【用户消息管理】修改")
    @PostMapping("modifyUserMessage")
    @SysUserLog(module = "用户消息管理", action = "修改")
    public BaseResponse modifyUserMessage(@Valid @ModelAttribute UserMessageModifyDto userMessageModifyDto) {
        userMessageService.modifyUserMessage(userMessageModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户消息管理】删除", notes = "【用户消息管理】删除")
    @PostMapping("deleteUserMessage")
    @SysUserLog(module = "用户消息管理", action = "删除")
    public BaseResponse deleteUserMessage(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userMessageService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}