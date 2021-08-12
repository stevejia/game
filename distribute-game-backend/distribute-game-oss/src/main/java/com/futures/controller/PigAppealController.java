package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.PigAppealModifyDto;
import com.futures.model.dto.PigAppealSaveDto;
import com.futures.model.entity.PigAppeal;
import com.futures.model.entity.PigOrder;
import com.futures.model.entity.Users;
import com.futures.service.PigAppealService;
import com.futures.service.PigOrderService;
import com.futures.service.UsersService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigAppeal")
@Api(tags = "交易申诉管理")
public class PigAppealController {

    @Autowired
    private PigAppealService pigAppealService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private PigOrderService pigOrderService;

    @ApiOperation(value = "【交易申诉管理】列表", notes = "【交易申诉管理】列表", response = PigAppeal.class)
    @PostMapping("queryPigAppeal")
    public BaseResponse queryPigAppeal(Page page, @Valid @ModelAttribute PigAppealModifyDto pigAppealModifyDto) {
        return BaseResponse.success(pigAppealService.queryPigAppeal(page, pigAppealModifyDto));
    }

    @ApiOperation(value = "【交易申诉管理】详情", notes = "【交易申诉管理】详情", response = PigAppealModifyDto.class)
    @PostMapping("getPigAppeal")
    public BaseResponse getPigAppeal(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        PigAppeal appeal = pigAppealService.getById(id);
        Users users = usersService.getById(appeal.getUserId());
        PigOrder order = pigOrderService.getById(appeal.getOrderId());
        PigAppealModifyDto dto = new PigAppealModifyDto();
        BeanUtils.copyProperties(appeal,dto);
        dto.setNickname(users.getNickname());
        dto.setUpdateTime(appeal.getUpdateTime().intValue());
        dto.setImgUrl(order.getImgUrl());
        dto.setOrderNo(order.getOrderId().toString());
        return BaseResponse.success(dto);
    }

    @ApiOperation(value = "【交易申诉管理】添加", notes = "【交易申诉管理】添加")
    @PostMapping("savePigAppeal")
    @SysUserLog(module = "交易申诉管理", action = "添加")
    public BaseResponse savePigAppeal(@Valid @ModelAttribute PigAppealSaveDto pigAppealSaveDto) {
        pigAppealService.savePigAppeal(pigAppealSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【交易申诉管理】修改", notes = "【交易申诉管理】修改")
    @PostMapping("modifyPigAppeal")
    @SysUserLog(module = "交易申诉管理", action = "修改")
    public BaseResponse modifyPigAppeal(@Valid @ModelAttribute PigAppealModifyDto pigAppealModifyDto) {
        pigAppealService.modifyPigAppeal(pigAppealModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【交易申诉管理】删除", notes = "【交易申诉管理】删除")
    @PostMapping("deletePigAppeal")
    @SysUserLog(module = "交易申诉管理", action = "删除")
    public BaseResponse deletePigAppeal(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        pigAppealService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【交易申诉管理】审核", notes = "【交易申诉管理】审核")
    @PostMapping("auth")
    @SysUserLog(module = "交易申诉管理", action = "审核")
    public BaseResponse authPigAppeal(@Valid @ModelAttribute PigAppealModifyDto pigAppealModifyDto) {
        pigAppealService.authPigAppeal(pigAppealModifyDto);
        return BaseResponse.success("修改成功");
    }
}