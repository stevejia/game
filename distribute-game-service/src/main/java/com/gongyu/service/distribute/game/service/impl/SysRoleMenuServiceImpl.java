package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gongyu.service.distribute.game.mapper.SysRoleMenuMapper;
import com.gongyu.service.distribute.game.model.dto.SysBtnDto;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.service.distribute.game.model.entity.SysRoleMenu;
import com.gongyu.service.distribute.game.service.SysRoleMenuService;
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
