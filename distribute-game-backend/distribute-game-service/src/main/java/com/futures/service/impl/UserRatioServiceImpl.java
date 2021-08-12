package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.UserRatioMapper;
import com.futures.model.dto.UserRatioModifyDto;
import com.futures.model.dto.UserRatioSaveDto;
import com.futures.model.entity.UserRatio;
import com.futures.service.UserRatioService;
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