package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserMessageModifyDto;
import com.futures.model.dto.UserMessageSaveDto;
import com.futures.model.entity.UserMessage;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserMessageService extends CrudService<UserMessage>{

    IPage<UserMessage> queryUserMessage(IPage<UserMessage> page);

    void saveUserMessage(UserMessageSaveDto userMessageSaveDto);

    void modifyUserMessage(UserMessageModifyDto userMessageModifyDto);
}