package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.SysUserMapper;
import com.gongyu.service.distribute.game.model.entity.SysUser;
import com.gongyu.service.distribute.game.service.SysUserService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends CrudServiceSupport<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysUser> querySysUser(IPage page, String name, String username, Long roleId, Long deptId) {
        page.setRecords(sysUserMapper.querySysUser(page, name, username, roleId, deptId));
        return page;
    }
}

