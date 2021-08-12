package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.SmsTemplateMapper;
import com.futures.model.dto.SmsTemplateModifyDto;
import com.futures.model.dto.SmsTemplateSaveDto;
import com.futures.model.entity.SmsTemplate;
import com.futures.service.SmsTemplateService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsTemplateServiceImpl extends CrudServiceSupport<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService  {

    @Override
    public IPage<SmsTemplate> querySmsTemplate(IPage<SmsTemplate> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveSmsTemplate(SmsTemplateSaveDto smsTemplateSaveDto) {
        SmsTemplate smsTemplate = new SmsTemplate();
        BeanUtils.copyProperties(smsTemplateSaveDto, smsTemplate);
        this.save(smsTemplate);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifySmsTemplate(SmsTemplateModifyDto smsTemplateModifyDto) {
        SmsTemplate smsTemplate = new SmsTemplate();
        BeanUtils.copyProperties(smsTemplateModifyDto, smsTemplate);
        this.updateById(smsTemplate);
    }
}