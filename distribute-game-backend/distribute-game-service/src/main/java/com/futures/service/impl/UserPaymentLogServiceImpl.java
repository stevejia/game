package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.UserPaymentLogMapper;
import com.futures.model.dto.UserPaymentLogModifyDto;
import com.futures.model.dto.UserPaymentLogSaveDto;
import com.futures.model.entity.UserPaymentLog;
import com.futures.service.UserPaymentLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPaymentLogServiceImpl extends CrudServiceSupport<UserPaymentLogMapper, UserPaymentLog> implements UserPaymentLogService {

    @Override
    public IPage<UserPaymentLog> queryUserPaymentLog(IPage<UserPaymentLog> page, UserPaymentLogSaveDto userPaymentLogSaveDto) {
        LambdaQueryWrapper<UserPaymentLog> eq = Wrappers.<UserPaymentLog>lambdaQuery();
        if (userPaymentLogSaveDto.getUserId() != null) {
            eq.eq(UserPaymentLog::getUserId, userPaymentLogSaveDto.getUserId());
        }
        if (userPaymentLogSaveDto.getStatus() != null) {
            eq.eq(UserPaymentLog::getStatus, userPaymentLogSaveDto.getStatus());
        }
        IPage<UserPaymentLog> page1 = this.page(page, eq);
        return page1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserPaymentLog(UserPaymentLogSaveDto userPaymentLogSaveDto) {
        UserPaymentLog userPaymentLog = new UserPaymentLog();
        BeanUtils.copyProperties(userPaymentLogSaveDto, userPaymentLog);
        userPaymentLog.setCreateTime(DateUtil.getNowDate());
        userPaymentLog.setUpTime(DateUtil.getNowDate());
        this.save(userPaymentLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserPaymentLog(UserPaymentLogModifyDto userPaymentLogModifyDto) {
        UserPaymentLog userPaymentLog = new UserPaymentLog();
        BeanUtils.copyProperties(userPaymentLogModifyDto, userPaymentLog);
        userPaymentLog.setUpTime(DateUtil.getNowDate());
        this.updateById(userPaymentLog);
    }
}