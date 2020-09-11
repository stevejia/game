package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.UserRatioMapper;
import com.gongyu.service.distribute.game.model.dto.UserRatioModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserRatioSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserRatio;
import com.gongyu.service.distribute.game.service.UserRatioService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRatioServiceImpl extends CrudServiceSupport<UserRatioMapper, UserRatio> implements UserRatioService  {

    @Override
    public IPage<UserRatio> queryUserRatio(IPage<UserRatio> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserRatio(UserRatioSaveDto userRatioSaveDto) {
        UserRatio userRatio = new UserRatio();
        BeanUtils.copyProperties(userRatioSaveDto, userRatio);
        this.save(userRatio);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserRatio(UserRatioModifyDto userRatioModifyDto) {
        UserRatio userRatio = new UserRatio();
        BeanUtils.copyProperties(userRatioModifyDto, userRatio);
        this.updateById(userRatio);
    }
}