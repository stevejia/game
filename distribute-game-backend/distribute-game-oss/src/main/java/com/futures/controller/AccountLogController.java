package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.AccountLogModifyDto;
import com.futures.model.dto.AccountLogSaveDto;
import com.futures.model.entity.AccountLog;
import com.futures.service.AccountLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("accountLog")
@Api(tags = "资金流水管理")
public class AccountLogController {

    @Autowired
    private AccountLogService accountLogService;

    @ApiOperation(value = "【资金流水管理】列表", notes = "【资金流水管理】列表", response = AccountLog.class)
    @PostMapping("queryAccountLog")
    public BaseResponse queryAccountLog(Page page, @ApiParam(value = "userId") @RequestParam() Long userId, @ApiParam(value = "mobile") @RequestParam() String mobile) {
        return BaseResponse.success(accountLogService.queryAccountLog(page, userId, mobile));
    }

    @ApiOperation(value = "【资金流水管理】详情", notes = "【资金流水管理】详情", response = AccountLog.class)
    @PostMapping("getAccountLog")
    public BaseResponse getAccountLog(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(accountLogService.getById(id));
    }

    @ApiOperation(value = "【资金流水管理】添加", notes = "【资金流水管理】添加")
    @PostMapping("saveAccountLog")
    @SysUserLog(module = "资金流水管理", action = "添加")
    public BaseResponse saveAccountLog(@Valid @ModelAttribute AccountLogSaveDto accountLogSaveDto) {
        accountLogService.saveAccountLog(accountLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【资金流水管理】修改", notes = "【资金流水管理】修改")
    @PostMapping("modifyAccountLog")
    @SysUserLog(module = "资金流水管理", action = "修改")
    public BaseResponse modifyAccountLog(@Valid @ModelAttribute AccountLogModifyDto accountLogModifyDto) {
        accountLogService.modifyAccountLog(accountLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【资金流水管理】删除", notes = "【资金流水管理】删除")
    @PostMapping("deleteAccountLog")
    @SysUserLog(module = "资金流水管理", action = "删除")
    public BaseResponse deleteAccountLog(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        accountLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}