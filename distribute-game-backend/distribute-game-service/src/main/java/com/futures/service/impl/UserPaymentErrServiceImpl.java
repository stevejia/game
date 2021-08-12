package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.UserPaymentErrMapper;
import com.futures.model.dto.UserPaymentErrModifyDto;
import com.futures.model.dto.UserPaymentErrSaveDto;
import com.futures.model.entity.UserPaymentErr;
import com.futures.service.UserPaymentErrService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPaymentErrServiceImpl extends CrudServiceSupport<UserPaymentErrMapper, UserPaymentErr> implements UserPaymentErrService  {

    @Override
    public IPage<UserPaymentErr> queryUserPaymentErr(IPage<UserPaymentErr> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserPaymentErr(UserPaymentErrSaveDto userPaymentErrSaveDto) {
        UserPaymentErr userPaymentErr = new UserPaymentErr();
        BeanUtils.copyProperties(userPaymentErrSaveDto, userPaymentErr);
        this.save(userPaymentErr);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserPaymentErr(UserPaymentErrModifyDto userPaymentErrModifyDto) {
        UserPaymentErr userPaymentErr = new UserPaymentErr();
        BeanUtils.copyProperties(userPaymentErrModifyDto, userPaymentErr);
        this.updateById(userPaymentErr);
    }
}