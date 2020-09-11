package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysRoleMapper;
import com.gongyu.service.distribute.game.model.entity.SysRole;
import com.gongyu.service.distribute.game.service.SysRoleService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends CrudServiceSupport<SysRoleMapper, SysRole> implements SysRoleService {

}
