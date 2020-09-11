package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDelete;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserExclusivePigDeleteService extends CrudService<UserExclusivePigDelete>{

    IPage<PigDeletePageDTO> queryUserExclusivePigDelete(IPage<PigDeletePageDTO> page, PigDeletePageReqDto param);

    void saveUserExclusivePigDelete(UserExclusivePigDeleteSaveDto userExclusivePigDeleteSaveDto);

    void modifyUserExclusivePigDelete(UserExclusivePigDeleteModifyDto userExclusivePigDeleteModifyDto);

    BaseResponse destoryPig(DestoryPigReqDto param);
}