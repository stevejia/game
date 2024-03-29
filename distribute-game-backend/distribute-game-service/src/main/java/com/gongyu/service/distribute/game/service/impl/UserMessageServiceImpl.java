package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.UserMessageMapper;
import com.gongyu.service.distribute.game.model.dto.UserMessageModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserMessageSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserMessage;
import com.gongyu.service.distribute.game.service.UserMessageService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMessageServiceImpl extends CrudServiceSupport<UserMessageMapper, UserMessage> implements UserMessageService  {

    @Override
    public IPage<UserMessage> queryUserMessage(IPage<UserMessage> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserMessage(UserMessageSaveDto userMessageSaveDto) {
        UserMessage userMessage = new UserMessage();
        BeanUtils.copyProperties(userMessageSaveDto, userMessage);
        this.save(userMessage);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserMessage(UserMessageModifyDto userMessageModifyDto) {
        UserMessage userMessage = new UserMessage();
        BeanUtils.copyProperties(userMessageModifyDto, userMessage);
        this.updateById(userMessage);
    }
}