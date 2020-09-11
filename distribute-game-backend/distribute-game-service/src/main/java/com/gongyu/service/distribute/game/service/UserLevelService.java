package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserLevelModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLevelSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLevel;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserLevelService extends CrudService<UserLevel>{

    IPage<UserLevel> queryUserLevel(IPage<UserLevel> page);

    void saveUserLevel(UserLevelSaveDto userLevelSaveDto);

    void modifyUserLevel(UserLevelModifyDto userLevelModifyDto);
}