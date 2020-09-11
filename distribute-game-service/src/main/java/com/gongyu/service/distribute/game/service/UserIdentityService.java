package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserIdentityModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserIdentitySaveDto;
import com.gongyu.service.distribute.game.model.entity.UserIdentity;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface UserIdentityService extends CrudService<UserIdentity> {

    IPage<UserIdentity> queryUserIdentity(IPage<UserIdentity> page, UserIdentityModifyDto userIdentityModifyDto);

    void saveUserIdentity(UserIdentitySaveDto userIdentitySaveDto);

    void modifyUserIdentity(UserIdentityModifyDto userIdentityModifyDto);

    UserIdentity convertUserIdentity(Long userId,String name,String idCard);
}