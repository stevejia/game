package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.SendMailModifyDto;
import com.gongyu.service.distribute.game.model.dto.SendMailSaveDto;
import com.gongyu.service.distribute.game.model.entity.SendMail;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SendMailService extends CrudService<SendMail>{

    IPage<SendMail> querySendMail(IPage<SendMail> page);

    void saveSendMail(SendMailSaveDto sendMailSaveDto);

    void modifySendMail(SendMailModifyDto sendMailModifyDto);
}