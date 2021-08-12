package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserLockLogModifyDto;
import com.futures.model.dto.UserLockLogSaveDto;
import com.futures.model.entity.UserLockLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserLockLogService extends CrudService<UserLockLog>{

    IPage<UserLockLog> queryUserLockLog(IPage<UserLockLog> page);

    void saveUserLockLog(UserLockLogSaveDto userLockLogSaveDto);

    void modifyUserLockLog(UserLockLogModifyDto userLockLogModifyDto);
}