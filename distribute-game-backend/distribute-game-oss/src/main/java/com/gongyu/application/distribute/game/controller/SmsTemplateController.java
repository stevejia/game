package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.SmsTemplateModifyDto;
import com.gongyu.service.distribute.game.model.dto.SmsTemplateSaveDto;
import com.gongyu.service.distribute.game.model.entity.SmsTemplate;
import com.gongyu.service.distribute.game.service.SmsTemplateService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("smsTemplate")
@Api(tags = "短信模板管理")
public class SmsTemplateController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    @ApiOperation(value = "【短信模板管理】列表", notes = "【短信模板管理】列表", response = SmsTemplate.class)
    @PostMapping("querySmsTemplate")
    public BaseResponse querySmsTemplate(Page page) {
        return BaseResponse.success(smsTemplateService.querySmsTemplate(page));
    }

    @ApiOperation(value = "【短信模板管理】详情", notes = "【短信模板管理】详情", response = SmsTemplate.class)
    @PostMapping("getSmsTemplate")
    public BaseResponse getSmsTemplate(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(smsTemplateService.getById(id));
    }

    @ApiOperation(value = "【短信模板管理】添加", notes = "【短信模板管理】添加")
    @PostMapping("saveSmsTemplate")
    @SysUserLog(module = "短信模板管理", action = "添加")
    public BaseResponse saveSmsTemplate(@Valid @ModelAttribute SmsTemplateSaveDto smsTemplateSaveDto) {
        smsTemplateService.saveSmsTemplate(smsTemplateSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【短信模板管理】修改", notes = "【短信模板管理】修改")
    @PostMapping("modifySmsTemplate")
    @SysUserLog(module = "短信模板管理", action = "修改")
    public BaseResponse modifySmsTemplate(@Valid @ModelAttribute SmsTemplateModifyDto smsTemplateModifyDto) {
        smsTemplateService.modifySmsTemplate(smsTemplateModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【短信模板管理】删除", notes = "【短信模板管理】删除")
    @PostMapping("deleteSmsTemplate")
    @SysUserLog(module = "短信模板管理", action = "删除")
    public BaseResponse deleteSmsTemplate(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        smsTemplateService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}