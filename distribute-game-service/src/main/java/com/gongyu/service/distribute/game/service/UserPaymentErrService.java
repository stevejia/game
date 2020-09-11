package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserPaymentErrModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentErrSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPaymentErr;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentErrService extends CrudService<UserPaymentErr>{

    IPage<UserPaymentErr> queryUserPaymentErr(IPage<UserPaymentErr> page);

    void saveUserPaymentErr(UserPaymentErrSaveDto userPaymentErrSaveDto);

    void modifyUserPaymentErr(UserPaymentErrModifyDto userPaymentErrModifyDto);
}