package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import com.gongyu.service.distribute.game.service.UserExclusivePigService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("userExclusivePig")
@Api(tags = "木材实例管理")
public class UserExclusivePigController {

    @Autowired
    private UserExclusivePigService userExclusivePigService;

    @ApiOperation(value = "【木材实例管理】列表", notes = "【木材实例管理】列表", response = UserExclusivePig.class)
    @PostMapping("queryUserExclusivePig")
    public BaseResponse queryUserExclusivePig(Page page, PigPageReqDto param) {
        return BaseResponse.success(userExclusivePigService.queryUserExclusivePig(page,param));
    }

    @ApiOperation(value = "【木材实例管理】详情", notes = "【木材实例管理】详情", response = UserExclusivePig.class)
    @PostMapping("getUserExclusivePig")
    public BaseResponse getUserExclusivePig(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(userExclusivePigService.getById(id));
    }

    @ApiOperation(value = "【木材实例管理】添加", notes = "【木材实例管理】添加")
    @PostMapping("saveUserExclusivePig")
    @SysUserLog(module = "木材实例管理", action = "添加")
    public BaseResponse saveUserExclusivePig(@Valid @ModelAttribute UserExclusivePigSaveDto userExclusivePigSaveDto) {
        userExclusivePigService.saveUserExclusivePig(userExclusivePigSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【木材实例管理】修改", notes = "【木材实例管理】修改")
    @PostMapping("modifyUserExclusivePig")
    @SysUserLog(module = "木材实例管理", action = "修改")
    public BaseResponse modifyUserExclusivePig(@Valid @ModelAttribute UserExclusivePigModifyDto userExclusivePigModifyDto) {
        userExclusivePigService.modifyUserExclusivePig(userExclusivePigModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【木材实例管理】删除", notes = "【木材实例管理】删除")
    @PostMapping("deleteUserExclusivePig")
    @SysUserLog(module = "木材实例管理", action = "删除")
    public BaseResponse deleteUserExclusivePig(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        userExclusivePigService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【木材实例管理-今日开抢】今日开奖列表", notes = "【木材实例管理-今日开抢】今日开奖列表", response = UserExclusivePigDTO.class)
    @PostMapping("prizeToday")
    public BaseResponse prizeToday(Page page, PrizeTodayReqDto param){
        return BaseResponse.success(userExclusivePigService.prizeToday(page,param));
    }

    @ApiOperation(value = "【木材实例管理-今日开抢】修改价格", notes = "【木材实例管理-今日开抢】修改价格")
    @PostMapping("updatePrice")
    public BaseResponse updatePrice(PigUpdatePriceReqDto param){
        userExclusivePigService.updatePrice(param);
        return BaseResponse.success();
    }

    @ApiOperation(value = "【木材实例管理-今日开抢】指定用户ID", notes = "【木材实例管理-今日开抢】指定用户ID")
    @PostMapping("updateAppointUserId")
    public BaseResponse updateAppointUserId(PigUpdateUserIdReqDto param){
        userExclusivePigService.updateAppointUserId(param);
        return BaseResponse.success();
    }
}