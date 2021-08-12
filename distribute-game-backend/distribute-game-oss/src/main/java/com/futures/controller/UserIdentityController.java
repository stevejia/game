package com.futures.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.UserIdentityModifyDto;
import com.futures.model.dto.UserIdentityResponseDto;
import com.futures.model.dto.UserIdentitySaveDto;
import com.futures.model.entity.UserIdentity;
import com.futures.model.entity.UserPayment;
import com.futures.model.entity.Users;
import com.futures.service.UserIdentityService;
import com.futures.service.UserPaymentService;
import com.futures.service.UsersService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("userIdentity")
@Api(tags = "身份审核管理")
public class UserIdentityController {

    @Autowired
    private UserIdentityService userIdentityService;
    @Autowired
    private UserPaymentService userPaymentService;
    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "【身份审核管理】列表", notes = "【身份审核管理】列表", response = UserIdentity.class)
    @PostMapping("queryUserIdentity")
    public BaseResponse queryUserIdentity(Page page, @Valid @ModelAttribute UserIdentityModifyDto userIdentityModifyDto) {
        return BaseResponse.success(userIdentityService.queryUserIdentity(page, userIdentityModifyDto));
    }

    @ApiOperation(value = "【身份审核管理】详情", notes = "【身份审核管理】详情", response = UserIdentity.class)
    @PostMapping("getUserIdentity")
    public BaseResponse getUserIdentity(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        UserIdentityResponseDto userIdentityResponseDto = new UserIdentityResponseDto();
        UserIdentity byId = userIdentityService.getById(id);
        //  查银行卡信息
        if (byId != null) {
            byId.setNickname(usersService.getById(byId.getUserId()).getNickname());
            List<UserPayment> list = userPaymentService.list(Wrappers.<UserPayment>lambdaQuery().eq(UserPayment::getUserId, byId.getUserId()));
            userIdentityResponseDto.setUserPaymentList(list);
        }
        userIdentityResponseDto.setUserIdentity(byId);
        return BaseResponse.success(userIdentityResponseDto);
    }

    @ApiOperation(value = "【身份审核管理】添加", notes = "【身份审核管理】添加")
    @PostMapping("saveUserIdentity")
    @SysUserLog(module = "身份审核管理", action = "添加")
    public BaseResponse saveUserIdentity(@Valid @ModelAttribute UserIdentitySaveDto userIdentitySaveDto) {
        userIdentityService.saveUserIdentity(userIdentitySaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【身份审核管理】修改", notes = "【身份审核管理】修改")
    @PostMapping("modifyUserIdentity")
    @SysUserLog(module = "身份审核管理", action = "修改")
    public BaseResponse modifyUserIdentity(@Valid @ModelAttribute UserIdentityModifyDto userIdentityModifyDto) {
        userIdentityService.modifyUserIdentity(userIdentityModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【身份审核管理】删除", notes = "【身份审核管理】删除")
    @PostMapping("deleteUserIdentity")
    @SysUserLog(module = "身份审核管理", action = "删除")
    public BaseResponse deleteUserIdentity(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        userIdentityService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}