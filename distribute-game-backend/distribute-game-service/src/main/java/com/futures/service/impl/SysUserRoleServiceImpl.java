package com.futures.service.impl;

import com.futures.mapper.SysUserRoleMapper;
import com.futures.model.entity.SysUserRole;
import com.futures.service.SysUserRoleService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends CrudServiceSupport<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
