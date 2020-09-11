package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserLockLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLockLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLockLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserLockLogService extends CrudService<UserLockLog>{

    IPage<UserLockLog> queryUserLockLog(IPage<UserLockLog> page);

    void saveUserLockLog(UserLockLogSaveDto userLockLogSaveDto);

    void modifyUserLockLog(UserLockLogModifyDto userLockLogModifyDto);
}