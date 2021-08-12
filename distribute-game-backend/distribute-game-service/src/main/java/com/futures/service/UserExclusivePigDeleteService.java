package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.*;
import com.futures.model.entity.UserExclusivePigDelete;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserExclusivePigDeleteService extends CrudService<UserExclusivePigDelete>{

    IPage<PigDeletePageDTO> queryUserExclusivePigDelete(IPage<PigDeletePageDTO> page, PigDeletePageReqDto param);

    void saveUserExclusivePigDelete(UserExclusivePigDeleteSaveDto userExclusivePigDeleteSaveDto);

    void modifyUserExclusivePigDelete(UserExclusivePigDeleteModifyDto userExclusivePigDeleteModifyDto);

    BaseResponse destoryPig(DestoryPigReqDto param);
}