package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.UserPaymentLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPaymentLog;
import com.gongyu.service.distribute.game.service.UserPaymentLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userPaymentLog")
@Api(tags = "用户收款支付方管理")
public class UserPaymentLogController {

    @Autowired
    private UserPaymentLogService userPaymentLogService;

    @ApiOperation(value = "【用户收款支付方管理】列表", notes = "【用户收款支付方管理】列表", response = UserPaymentLog.class)
    @PostMapping("queryUserPaymentLog")
    public BaseResponse queryUserPaymentLog(Page page, UserPaymentLogSaveDto userPaymentLogSaveDto) {
        return BaseResponse.success(userPaymentLogService.queryUserPaymentLog(page, userPaymentLogSaveDto));
    }

    @ApiOperation(value = "【用户收款支付方管理】详情", notes = "【用户收款支付方管理】详情", response = UserPaymentLog.class)
    @PostMapping("getUserPaymentLog")
    public BaseResponse getUserPaymentLog(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(userPaymentLogService.getById(id));
    }

    @ApiOperation(value = "【用户收款支付方管理】添加", notes = "【用户收款支付方管理】添加")
    @PostMapping("saveUserPaymentLog")
    @SysUserLog(module = "用户收款支付方管理", action = "添加")
    public BaseResponse saveUserPaymentLog(@Valid @ModelAttribute UserPaymentLogSaveDto userPaymentLogSaveDto) {
        userPaymentLogService.saveUserPaymentLog(userPaymentLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户收款支付方管理】修改", notes = "【用户收款支付方管理】修改")
    @PostMapping("modifyUserPaymentLog")
    @SysUserLog(module = "用户收款支付方管理", action = "修改")
    public BaseResponse modifyUserPaymentLog(@Valid @ModelAttribute UserPaymentLogModifyDto userPaymentLogModifyDto) {
        userPaymentLogService.modifyUserPaymentLog(userPaymentLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户收款支付方管理】删除", notes = "【用户收款支付方管理】删除")
    @PostMapping("deleteUserPaymentLog")
    @SysUserLog(module = "用户收款支付方管理", action = "删除")
    public BaseResponse deleteUserPaymentLog(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        userPaymentLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}