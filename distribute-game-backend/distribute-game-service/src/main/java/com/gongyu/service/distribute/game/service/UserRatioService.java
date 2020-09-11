package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserRatioModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserRatioSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserRatio;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserRatioService extends CrudService<UserRatio>{

    IPage<UserRatio> queryUserRatio(IPage<UserRatio> page);

    void saveUserRatio(UserRatioSaveDto userRatioSaveDto);

    void modifyUserRatio(UserRatioModifyDto userRatioModifyDto);
}