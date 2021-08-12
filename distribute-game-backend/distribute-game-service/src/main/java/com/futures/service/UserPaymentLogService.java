package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserPaymentLogModifyDto;
import com.futures.model.dto.UserPaymentLogSaveDto;
import com.futures.model.entity.UserPaymentLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentLogService extends CrudService<UserPaymentLog> {

    IPage<UserPaymentLog> queryUserPaymentLog(IPage<UserPaymentLog> page, UserPaymentLogSaveDto userPaymentLogSaveDto);

    void saveUserPaymentLog(UserPaymentLogSaveDto userPaymentLogSaveDto);

    void modifyUserPaymentLog(UserPaymentLogModifyDto userPaymentLogModifyDto);
}