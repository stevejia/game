package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserPaymentErrModifyDto;
import com.futures.model.dto.UserPaymentErrSaveDto;
import com.futures.model.entity.UserPaymentErr;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentErrService extends CrudService<UserPaymentErr>{

    IPage<UserPaymentErr> queryUserPaymentErr(IPage<UserPaymentErr> page);

    void saveUserPaymentErr(UserPaymentErrSaveDto userPaymentErrSaveDto);

    void modifyUserPaymentErr(UserPaymentErrModifyDto userPaymentErrModifyDto);
}