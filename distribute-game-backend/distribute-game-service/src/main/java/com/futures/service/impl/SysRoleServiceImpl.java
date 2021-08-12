package com.futures.service.impl;

import com.futures.mapper.SysRoleMapper;
import com.futures.model.entity.SysRole;
import com.futures.service.SysRoleService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends CrudServiceSupport<SysRoleMapper, SysRole> implements SysRoleService {

}
