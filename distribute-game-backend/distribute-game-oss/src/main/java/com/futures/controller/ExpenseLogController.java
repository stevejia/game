package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.ExpenseLogModifyDto;
import com.futures.model.dto.ExpenseLogSaveDto;
import com.futures.model.entity.ExpenseLog;
import com.futures.service.ExpenseLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("expenseLog")
@Api(tags = "平台支出记录日管理")
public class ExpenseLogController {

    @Autowired
    private ExpenseLogService expenseLogService;

    @ApiOperation(value = "【平台支出记录日管理】列表", notes = "【平台支出记录日管理】列表", response = ExpenseLog.class)
    @PostMapping("queryExpenseLog")
    public BaseResponse queryExpenseLog(Page page) {
        return BaseResponse.success(expenseLogService.queryExpenseLog(page));
    }

    @ApiOperation(value = "【平台支出记录日管理】详情", notes = "【平台支出记录日管理】详情", response = ExpenseLog.class)
    @PostMapping("getExpenseLog")
    public BaseResponse getExpenseLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(expenseLogService.getById(id));
    }

    @ApiOperation(value = "【平台支出记录日管理】添加", notes = "【平台支出记录日管理】添加")
    @PostMapping("saveExpenseLog")
    @SysUserLog(module = "平台支出记录日管理", action = "添加")
    public BaseResponse saveExpenseLog(@Valid @ModelAttribute ExpenseLogSaveDto expenseLogSaveDto) {
        expenseLogService.saveExpenseLog(expenseLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【平台支出记录日管理】修改", notes = "【平台支出记录日管理】修改")
    @PostMapping("modifyExpenseLog")
    @SysUserLog(module = "平台支出记录日管理", action = "修改")
    public BaseResponse modifyExpenseLog(@Valid @ModelAttribute ExpenseLogModifyDto expenseLogModifyDto) {
        expenseLogService.modifyExpenseLog(expenseLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【平台支出记录日管理】删除", notes = "【平台支出记录日管理】删除")
    @PostMapping("deleteExpenseLog")
    @SysUserLog(module = "平台支出记录日管理", action = "删除")
    public BaseResponse deleteExpenseLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        expenseLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}