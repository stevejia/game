package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysUserRoleMapper;
import com.gongyu.service.distribute.game.model.entity.SysUserRole;
import com.gongyu.service.distribute.game.service.SysUserRoleService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends CrudServiceSupport<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
