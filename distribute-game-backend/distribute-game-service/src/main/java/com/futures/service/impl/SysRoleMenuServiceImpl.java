package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futures.mapper.SysRoleMenuMapper;
import com.futures.model.dto.SysBtnDto;
import com.futures.model.entity.SysMenu;
import com.futures.model.entity.SysRoleMenu;
import com.futures.service.SysRoleMenuService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleMenuServiceImpl extends CrudServiceSupport<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> getRoleDetail(Long parent, Long roleId) {
        return sysRoleMenuMapper.getRoleDetail(parent, roleId);
    }

    @Override
    public List<SysMenu> getSuperAdminMenuList(Long parent, Long roleId) {
        return sysRoleMenuMapper.getSuperAdminMenuList(parent, roleId);
    }

    @Override
    public List<SysMenu> getRoleMenuList(Long parent, Long roleId) {
        return sysRoleMenuMapper.getRoleMenuList(parent, roleId);
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        return sysRoleMenuMapper.getUserMenuList(userId);
    }

    @Override
    public List<SysBtnDto> getBtnList(Long roleId) {
        return sysRoleMenuMapper.getBtnList(roleId);
    }

    @Override
    public void deleteRoleMenu(Long roleId) {
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("roleId", roleId));
    }
}
