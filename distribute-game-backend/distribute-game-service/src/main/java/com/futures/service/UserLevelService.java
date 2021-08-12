package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserLevelModifyDto;
import com.futures.model.dto.UserLevelSaveDto;
import com.futures.model.entity.UserLevel;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserLevelService extends CrudService<UserLevel>{

    IPage<UserLevel> queryUserLevel(IPage<UserLevel> page);

    void saveUserLevel(UserLevelSaveDto userLevelSaveDto);

    void modifyUserLevel(UserLevelModifyDto userLevelModifyDto);
}