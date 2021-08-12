package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.SmsLogModifyDto;
import com.futures.model.dto.SmsLogSaveDto;
import com.futures.model.entity.SmsLog;
import com.futures.service.SmsLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("smsLog")
@Api(tags = "短信发送日志管理")
public class SmsLogController {

    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "【短信发送日志管理】列表", notes = "【短信发送日志管理】列表", response = SmsLog.class)
    @PostMapping("querySmsLog")
    public BaseResponse querySmsLog(Page page) {
        return BaseResponse.success(smsLogService.querySmsLog(page));
    }

    @ApiOperation(value = "【短信发送日志管理】详情", notes = "【短信发送日志管理】详情", response = SmsLog.class)
    @PostMapping("getSmsLog")
    public BaseResponse getSmsLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(smsLogService.getById(id));
    }

    @ApiOperation(value = "【短信发送日志管理】添加", notes = "【短信发送日志管理】添加")
    @PostMapping("saveSmsLog")
    @SysUserLog(module = "短信发送日志管理", action = "添加")
    public BaseResponse saveSmsLog(@Valid @ModelAttribute SmsLogSaveDto smsLogSaveDto) {
        smsLogService.saveSmsLog(smsLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【短信发送日志管理】修改", notes = "【短信发送日志管理】修改")
    @PostMapping("modifySmsLog")
    @SysUserLog(module = "短信发送日志管理", action = "修改")
    public BaseResponse modifySmsLog(@Valid @ModelAttribute SmsLogModifyDto smsLogModifyDto) {
        smsLogService.modifySmsLog(smsLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【短信发送日志管理】删除", notes = "【短信发送日志管理】删除")
    @PostMapping("deleteSmsLog")
    @SysUserLog(module = "短信发送日志管理", action = "删除")
    public BaseResponse deleteSmsLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        smsLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}