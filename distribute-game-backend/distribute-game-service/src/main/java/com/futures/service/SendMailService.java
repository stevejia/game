package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.SendMailModifyDto;
import com.futures.model.dto.SendMailSaveDto;
import com.futures.model.entity.SendMail;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SendMailService extends CrudService<SendMail>{

    IPage<SendMail> querySendMail(IPage<SendMail> page);

    void saveSendMail(SendMailSaveDto sendMailSaveDto);

    void modifySendMail(SendMailModifyDto sendMailModifyDto);
}