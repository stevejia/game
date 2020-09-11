package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.UserLockLogMapper;
import com.gongyu.service.distribute.game.model.dto.UserLockLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLockLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLockLog;
import com.gongyu.service.distribute.game.service.UserLockLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLockLogServiceImpl extends CrudServiceSupport<UserLockLogMapper, UserLockLog> implements UserLockLogService  {

    @Override
    public IPage<UserLockLog> queryUserLockLog(IPage<UserLockLog> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserLockLog(UserLockLogSaveDto userLockLogSaveDto) {
        UserLockLog userLockLog = new UserLockLog();
        BeanUtils.copyProperties(userLockLogSaveDto, userLockLog);
        this.save(userLockLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserLockLog(UserLockLogModifyDto userLockLogModifyDto) {
        UserLockLog userLockLog = new UserLockLog();
        BeanUtils.copyProperties(userLockLogModifyDto, userLockLog);
        this.updateById(userLockLog);
    }
}