package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.SendMailModifyDto;
import com.gongyu.service.distribute.game.model.dto.SendMailSaveDto;
import com.gongyu.service.distribute.game.model.entity.SendMail;
import com.gongyu.service.distribute.game.service.SendMailService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sendMail")
@Api(tags = "管理员发送站内信管理")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @ApiOperation(value = "【管理员发送站内信管理】列表", notes = "【管理员发送站内信管理】列表", response = SendMail.class)
    @PostMapping("querySendMail")
    public BaseResponse querySendMail(Page page) {
        return BaseResponse.success(sendMailService.querySendMail(page));
    }

    @ApiOperation(value = "【管理员发送站内信管理】详情", notes = "【管理员发送站内信管理】详情", response = SendMail.class)
    @PostMapping("getSendMail")
    public BaseResponse getSendMail(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(sendMailService.getById(id));
    }

    @ApiOperation(value = "【管理员发送站内信管理】添加", notes = "【管理员发送站内信管理】添加")
    @PostMapping("saveSendMail")
    @SysUserLog(module = "管理员发送站内信管理", action = "添加")
    public BaseResponse saveSendMail(@Valid @ModelAttribute SendMailSaveDto sendMailSaveDto) {
        sendMailService.saveSendMail(sendMailSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【管理员发送站内信管理】修改", notes = "【管理员发送站内信管理】修改")
    @PostMapping("modifySendMail")
    @SysUserLog(module = "管理员发送站内信管理", action = "修改")
    public BaseResponse modifySendMail(@Valid @ModelAttribute SendMailModifyDto sendMailModifyDto) {
        sendMailService.modifySendMail(sendMailModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【管理员发送站内信管理】删除", notes = "【管理员发送站内信管理】删除")
    @PostMapping("deleteSendMail")
    @SysUserLog(module = "管理员发送站内信管理", action = "删除")
    public BaseResponse deleteSendMail(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        sendMailService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}