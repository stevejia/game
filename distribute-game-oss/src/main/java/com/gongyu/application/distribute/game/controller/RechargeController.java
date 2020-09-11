package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.AuthRechargeDto;
import com.gongyu.service.distribute.game.model.dto.RechargeDto;
import com.gongyu.service.distribute.game.model.dto.RechargeModifyDto;
import com.gongyu.service.distribute.game.model.dto.RechargeSaveDto;
import com.gongyu.service.distribute.game.model.entity.Recharge;
import com.gongyu.service.distribute.game.service.RechargeService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("recharge")
@Api(tags = "用户充值记录管理")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @ApiOperation(value = "【用户充值记录管理】列表", notes = "【用户充值记录管理】列表", response = Recharge.class)
    @PostMapping("queryRecharge")
    public BaseResponse queryRecharge(Page page, RechargeDto dto) {
        return BaseResponse.success(rechargeService.queryRecharge(page, dto));
    }

    @ApiOperation(value = "【用户充值记录管理】详情", notes = "【用户充值记录管理】详情", response = Recharge.class)
    @PostMapping("getRecharge")
    public BaseResponse getRecharge(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(rechargeService.getById(id));
    }

    @ApiOperation(value = "【用户充值记录管理】添加", notes = "【用户充值记录管理】添加")
    @PostMapping("saveRecharge")
    @SysUserLog(module = "用户充值记录管理", action = "添加")
    public BaseResponse saveRecharge(@Valid @ModelAttribute RechargeSaveDto rechargeSaveDto) {
        rechargeService.saveRecharge(rechargeSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【用户充值记录管理】修改", notes = "【用户充值记录管理】修改")
    @PostMapping("modifyRecharge")
    @SysUserLog(module = "用户充值记录管理", action = "修改")
    public BaseResponse modifyRecharge(@Valid @ModelAttribute RechargeModifyDto rechargeModifyDto) {
        rechargeService.modifyRecharge(rechargeModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【用户充值记录管理】删除", notes = "【用户充值记录管理】删除")
    @PostMapping("deleteRecharge")
    @SysUserLog(module = "用户充值记录管理", action = "删除")
    public BaseResponse deleteRecharge(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        rechargeService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【用户充值记录管理】审核", notes = "【用户充值记录管理】审核")
    @PostMapping("auth")
    @SysUserLog(module = "用户充值记录管理", action = "审核")
    public BaseResponse auth(@Valid AuthRechargeDto param) {
        return rechargeService.auth(param);
    }
}