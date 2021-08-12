package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserPaymentModifyDto;
import com.futures.model.dto.UserPaymentSaveDto;
import com.futures.model.entity.UserPayment;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentService extends CrudService<UserPayment>{

    IPage<UserPayment> queryUserPayment(Integer userId, Integer status, IPage<UserPayment> page);

    void saveUserPayment(UserPaymentSaveDto userPaymentSaveDto);

    void modifyUserPayment(UserPaymentModifyDto userPaymentModifyDto);

    /**
     * 检查是否拥有合法的收款方式
     * @return true 合法 false 不合法
     */
    boolean checkProperPayment();
}