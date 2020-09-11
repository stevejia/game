package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.SmsTemplateModifyDto;
import com.gongyu.service.distribute.game.model.dto.SmsTemplateSaveDto;
import com.gongyu.service.distribute.game.model.entity.SmsTemplate;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SmsTemplateService extends CrudService<SmsTemplate>{

    IPage<SmsTemplate> querySmsTemplate(IPage<SmsTemplate> page);

    void saveSmsTemplate(SmsTemplateSaveDto smsTemplateSaveDto);

    void modifySmsTemplate(SmsTemplateModifyDto smsTemplateModifyDto);
}