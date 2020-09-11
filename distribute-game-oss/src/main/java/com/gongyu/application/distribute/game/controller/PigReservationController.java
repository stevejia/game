package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigReservationModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigReservationSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.service.distribute.game.service.PigReservationService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigReservation")
@Api(tags = "预约记录管理")
public class PigReservationController {

    @Autowired
    private PigReservationService pigReservationService;

    @ApiOperation(value = "【预约记录管理】列表", notes = "【预约记录管理】列表", response = PigReservation.class)
    @PostMapping("queryPigReservation")
    public BaseResponse queryPigReservation(Page page,PigReservationModifyDto dto) {
        return BaseResponse.success(pigReservationService.queryPigReservation(page,dto));
    }

    @ApiOperation(value = "【预约记录管理】详情", notes = "【预约记录管理】详情", response = PigReservation.class)
    @PostMapping("getPigReservation")
    public BaseResponse getPigReservation(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigReservationService.getById(id));
    }

    @ApiOperation(value = "【预约记录管理】添加", notes = "【预约记录管理】添加")
    @PostMapping("savePigReservation")
    @SysUserLog(module = "预约记录管理", action = "添加")
    public BaseResponse savePigReservation(@Valid @ModelAttribute PigReservationSaveDto pigReservationSaveDto) {
        pigReservationService.savePigReservation(pigReservationSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【预约记录管理】修改", notes = "【预约记录管理】修改")
    @PostMapping("modifyPigReservation")
    @SysUserLog(module = "预约记录管理", action = "修改")
    public BaseResponse modifyPigReservation(@Valid @ModelAttribute PigReservationModifyDto pigReservationModifyDto) {
        pigReservationService.modifyPigReservation(pigReservationModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【预约记录管理】删除", notes = "【预约记录管理】删除")
    @PostMapping("deletePigReservation")
    @SysUserLog(module = "预约记录管理", action = "删除")
    public BaseResponse deletePigReservation(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigReservationService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}