package com.futures.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.*;
import com.futures.model.entity.UserExclusivePig;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserExclusivePigService extends CrudService<UserExclusivePig>{

    IPage<UserExclusivePigDTO> queryUserExclusivePig(IPage<UserExclusivePigDTO> page, PigPageReqDto param);

    IPage<UserExclusivePigDTO> prizeToday(IPage<UserExclusivePigDTO> page, PrizeTodayReqDto param);
    List<UserExclusivePigDTO> prizeAllToday(PrizeTodayReqDto param);
    void saveUserExclusivePig(UserExclusivePigSaveDto userExclusivePigSaveDto);

    void modifyUserExclusivePig(UserExclusivePigModifyDto userExclusivePigModifyDto);
    void updatePrice(PigUpdatePriceReqDto param);
    void updateAppointUserId(PigUpdateUserIdReqDto param);
}