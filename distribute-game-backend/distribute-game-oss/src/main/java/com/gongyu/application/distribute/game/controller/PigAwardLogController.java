package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogPageDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigAwardLog;
import com.gongyu.service.distribute.game.service.PigAwardLogService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigAwardLog")
@Api(tags = "中奖纪录")
public class PigAwardLogController {

    @Autowired
    private PigAwardLogService pigAwardLogService;

    @ApiOperation(value = "【中奖纪录】列表", notes = "【中奖纪录】列表", response = PigAwardLogPageDto.class)
    @PostMapping("queryPigAwardLog")
    public BaseResponse queryPigAwardLog(Page page,PigAwardLogPageDto dto) {
        return BaseResponse.success(pigAwardLogService.queryPigAwardLog(page,dto));
    }
    
    @ApiOperation(value = "【今日开抢】列表", notes = "【今日开抢】列表", response = PigAwardLogPageDto.class)
    @PostMapping("queryPigGoodsSummary")
    public BaseResponse queryPigGoodsSummary(PigAwardLogPageDto dto) {
    	return BaseResponse.success(pigAwardLogService.queryPigGoodsSummary(dto));
    }
    
    @ApiOperation(value = "【中奖纪录】详情", notes = "【中奖纪录】详情", response = PigAwardLog.class)
    @PostMapping("getPigAwardLog")
    public BaseResponse getPigAwardLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigAwardLogService.getById(id));
    }

    @ApiOperation(value = "【中奖纪录】添加", notes = "【中奖纪录】添加")
    @PostMapping("savePigAwardLog")
    @SysUserLog(module = "奖励日志管理", action = "添加")
    public BaseResponse savePigAwardLog(@Valid @ModelAttribute PigAwardLogSaveDto pigAwardLogSaveDto) {
        pigAwardLogService.savePigAwardLog(pigAwardLogSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【中奖纪录】修改", notes = "【中奖纪录】修改")
    @PostMapping("modifyPigAwardLog")
    @SysUserLog(module = "奖励日志管理", action = "修改")
    public BaseResponse modifyPigAwardLog(@Valid @ModelAttribute PigAwardLogModifyDto pigAwardLogModifyDto) {
        pigAwardLogService.modifyPigAwardLog(pigAwardLogModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【中奖纪录】删除", notes = "【中奖纪录】删除")
    @PostMapping("deletePigAwardLog")
    @SysUserLog(module = "奖励日志管理", action = "删除")
    public BaseResponse deletePigAwardLog(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigAwardLogService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}