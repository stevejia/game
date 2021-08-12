package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.UserRatioModifyDto;
import com.futures.model.dto.UserRatioSaveDto;
import com.futures.model.entity.UserRatio;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserRatioService extends CrudService<UserRatio>{

    IPage<UserRatio> queryUserRatio(IPage<UserRatio> page);

    void saveUserRatio(UserRatioSaveDto userRatioSaveDto);

    void modifyUserRatio(UserRatioModifyDto userRatioModifyDto);
}