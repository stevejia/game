package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserPaymentModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentService extends CrudService<UserPayment>{

    IPage<UserPayment> queryUserPayment(IPage<UserPayment> page);

    void saveUserPayment(UserPaymentSaveDto userPaymentSaveDto);

    void modifyUserPayment(UserPaymentModifyDto userPaymentModifyDto);

    /**
     * 检查是否拥有合法的收款方式
     * @return true 合法 false 不合法
     */
    boolean checkProperPayment();
}