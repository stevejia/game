package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.SendMailMapper;
import com.gongyu.service.distribute.game.model.dto.SendMailModifyDto;
import com.gongyu.service.distribute.game.model.dto.SendMailSaveDto;
import com.gongyu.service.distribute.game.model.entity.SendMail;
import com.gongyu.service.distribute.game.service.SendMailService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SendMailServiceImpl extends CrudServiceSupport<SendMailMapper, SendMail> implements SendMailService {

    @Override
    public IPage<SendMail> querySendMail(IPage<SendMail> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveSendMail(SendMailSaveDto sendMailSaveDto) {
        SendMail sendMail = new SendMail();
        BeanUtils.copyProperties(sendMailSaveDto, sendMail);
        sendMail.setCreateTime(DateUtil.getNowDate());
        this.save(sendMail);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifySendMail(SendMailModifyDto sendMailModifyDto) {
        SendMail sendMail = new SendMail();
        BeanUtils.copyProperties(sendMailModifyDto, sendMail);
        this.updateById(sendMail);
    }
}