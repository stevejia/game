package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.SmsTemplateModifyDto;
import com.futures.model.dto.SmsTemplateSaveDto;
import com.futures.model.entity.SmsTemplate;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SmsTemplateService extends CrudService<SmsTemplate>{

    IPage<SmsTemplate> querySmsTemplate(IPage<SmsTemplate> page);

    void saveSmsTemplate(SmsTemplateSaveDto smsTemplateSaveDto);

    void modifySmsTemplate(SmsTemplateModifyDto smsTemplateModifyDto);
}