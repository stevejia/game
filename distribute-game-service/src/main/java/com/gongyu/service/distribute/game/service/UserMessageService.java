package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserMessageModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserMessageSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserMessage;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserMessageService extends CrudService<UserMessage>{

    IPage<UserMessage> queryUserMessage(IPage<UserMessage> page);

    void saveUserMessage(UserMessageSaveDto userMessageSaveDto);

    void modifyUserMessage(UserMessageModifyDto userMessageModifyDto);
}