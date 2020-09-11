package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserPaymentLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPaymentLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserPaymentLogService extends CrudService<UserPaymentLog> {

    IPage<UserPaymentLog> queryUserPaymentLog(IPage<UserPaymentLog> page, UserPaymentLogSaveDto userPaymentLogSaveDto);

    void saveUserPaymentLog(UserPaymentLogSaveDto userPaymentLogSaveDto);

    void modifyUserPaymentLog(UserPaymentLogModifyDto userPaymentLogModifyDto);
}