package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.UserPaymentErrModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentErrSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPaymentErr;
import com.gongyu.service.distribute.game.service.UserPaymentErrService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userPaymentErr")
@Api(tags = "用户收款支付方管理")
public class UserPaymentErrController {

    @Autowired
    private UserPaymentErrService userPaymentErrService;

    @ApiOperation(value = "【用户收款支付方管理】列表", notes = "【用户收款支付方管理】列表", response = UserPaymentErr.class)
    @PostMapping("queryUserPaymentErr")
    public BaseResponse queryUserPaymentErr(Page page) {
        return BaseResponse.success(userPaymentErrService.queryUserPaymentErr(page));
    }

    @ApiOperation(value = "【用户收款支付方管理】详情", notes = "【用户收款支付方管理】详情", response = UserPaymentErr.class)
    @PostMapping("getUserPaymentErr")
    public BaseResponse getUserPaymentErr(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userPaymentErrService.getById(id));
    }

    @ApiOperation(value = "【用户收款支付方管理】添加", notes = "【用户收款支付方管理】添加")
    @PostMapping("saveUserPaymentErr")
    @SysUserLog(module = "用户收款支付方管理", action = "添加")
    public BaseResponse saveUserPaymentErr(@Valid @ModelAttribute UserPaymentErrSaveDto userPaymentErrSaveDto) {
        userPaymentErrService.saveUserPaymentErr(userPaymentErrSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户收款支付方管理】修改", notes = "【用户收款支付方管理】修改")
    @PostMapping("modifyUserPaymentErr")
    @SysUserLog(module = "用户收款支付方管理", action = "修改")
    public BaseResponse modifyUserPaymentErr(@Valid @ModelAttribute UserPaymentErrModifyDto userPaymentErrModifyDto) {
        userPaymentErrService.modifyUserPaymentErr(userPaymentErrModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户收款支付方管理】删除", notes = "【用户收款支付方管理】删除")
    @PostMapping("deleteUserPaymentErr")
    @SysUserLog(module = "用户收款支付方管理", action = "删除")
    public BaseResponse deleteUserPaymentErr(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userPaymentErrService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}