package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.UserLevelMapper;
import com.futures.model.dto.UserLevelModifyDto;
import com.futures.model.dto.UserLevelSaveDto;
import com.futures.model.entity.UserLevel;
import com.futures.service.UserLevelService;
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