package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.UserLevelMapper;
import com.gongyu.service.distribute.game.model.dto.UserLevelModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserLevelSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserLevel;
import com.gongyu.service.distribute.game.service.UserLevelService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLevelServiceImpl extends CrudServiceSupport<UserLevelMapper, UserLevel> implements UserLevelService  {

    @Override
    public IPage<UserLevel> queryUserLevel(IPage<UserLevel> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserLevel(UserLevelSaveDto userLevelSaveDto) {
        UserLevel userLevel = new UserLevel();
        BeanUtils.copyProperties(userLevelSaveDto, userLevel);
        this.save(userLevel);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserLevel(UserLevelModifyDto userLevelModifyDto) {
        UserLevel userLevel = new UserLevel();
        BeanUtils.copyProperties(userLevelModifyDto, userLevel);
        this.updateById(userLevel);
    }
}