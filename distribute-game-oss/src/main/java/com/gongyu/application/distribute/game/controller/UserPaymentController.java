package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.UserPaymentModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import com.gongyu.service.distribute.game.service.UserPaymentService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userPayment")
@Api(tags = "用户收款支付方式管理")
public class UserPaymentController {

    @Autowired
    private UserPaymentService userPaymentService;

    @ApiOperation(value = "【用户收款支付方式管理】列表", notes = "【用户收款支付方式管理】列表", response = UserPayment.class)
    @PostMapping("queryUserPayment")
    public BaseResponse queryUserPayment(Page page) {
        return BaseResponse.success(userPaymentService.queryUserPayment(page));
    }

    @ApiOperation(value = "【用户收款支付方式管理】详情", notes = "【用户收款支付方式管理】详情", response = UserPayment.class)
    @PostMapping("getUserPayment")
    public BaseResponse getUserPayment(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userPaymentService.getById(id));
    }

    @ApiOperation(value = "【用户收款支付方式管理】添加", notes = "【用户收款支付方式管理】添加")
    @PostMapping("saveUserPayment")
    @SysUserLog(module = "用户收款支付方式管理", action = "添加")
    public BaseResponse saveUserPayment(@Valid @ModelAttribute UserPaymentSaveDto userPaymentSaveDto) {
        userPaymentService.saveUserPayment(userPaymentSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户收款支付方式管理】修改", notes = "【用户收款支付方式管理】修改")
    @PostMapping("modifyUserPayment")
    @SysUserLog(module = "用户收款支付方式管理", action = "修改")
    public BaseResponse modifyUserPayment(@Valid @ModelAttribute UserPaymentModifyDto userPaymentModifyDto) {
        userPaymentService.modifyUserPayment(userPaymentModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户收款支付方式管理】删除", notes = "【用户收款支付方式管理】删除")
    @PostMapping("deleteUserPayment")
    @SysUserLog(module = "用户收款支付方式管理", action = "删除")
    public BaseResponse deleteUserPayment(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userPaymentService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}