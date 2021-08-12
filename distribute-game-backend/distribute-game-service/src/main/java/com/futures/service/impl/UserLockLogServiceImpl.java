package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.UserLockLogMapper;
import com.futures.model.dto.UserLockLogModifyDto;
import com.futures.model.dto.UserLockLogSaveDto;
import com.futures.model.entity.UserLockLog;
import com.futures.service.UserLockLogService;
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