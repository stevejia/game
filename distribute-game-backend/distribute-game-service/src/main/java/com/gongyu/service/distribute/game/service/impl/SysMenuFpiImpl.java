package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gongyu.service.distribute.game.model.dto.SysBtnDto;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.service.distribute.game.model.entity.SysUserRole;
import com.gongyu.service.distribute.game.service.SysMenuFpi;
import com.gongyu.service.distribute.game.service.SysMenuService;
import com.gongyu.service.distribute.game.service.SysRoleMenuService;
import com.gongyu.service.distribute.game.service.SysUserRoleService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SysMenuFpiImpl implements SysMenuFpi {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysMenu> getMenuList() {
        List<SysMenu> sysMenuList = new ArrayList<>();
        List<SysMenu> parentAuthorities = sysMenuService.list(new QueryWrapper<SysMenu>().isNull("parent"));
        for (SysMenu parentSysMenu : parentAuthorities) {
            iterateChildAuthority(parentSysMenu);
            sysMenuList.add(parentSysMenu);
        }
        return sysMenuList;
    }

    @Override
    public List<SysBtnDto> getBtnList(Long roleId) {
        return sysRoleMenuService.getBtnList(roleId);
    }

    private SysMenu iterateChildAuthority(SysMenu parentSysMenu) {
        List<SysMenu> childAuthorities = sysMenuService.list(new QueryWrapper<SysMenu>().eq("parent", parentSysMenu.getId()));
        parentSysMenu.setChildAuthorities(childAuthorities);
        if (CollectionUtils.isNotEmpty(childAuthorities)) {
            childAuthorities.forEach(this::iterateChildAuthority);
        }
        return parentSysMenu;
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        return sysRoleMenuService.getUserMenuList(userId);
    }

    @Override
    public List<SysMenu> getRoleMenuList(Long roleId) {
        List<SysMenu> sysMenuList = new ArrayList<>();
        List<SysMenu> sysMenuDtoList = sysRoleMenuService.getRoleDetail(null, roleId);
        for (SysMenu sysMenuDto : sysMenuDtoList) {
            sysMenuDto = queryRoleDetail(sysMenuDto, roleId);
            sysMenuList.add(sysMenuDto);
        }
        return sysMenuList;
    }

    private SysMenu queryRoleDetail(SysMenu sysMenuDto, Long roleId) {
        List<SysMenu> sysMenuDtoList = sysRoleMenuService.getRoleDetail(sysMenuDto.getId(), roleId);
        sysMenuDto.setChildAuthorities(sysMenuDtoList);
        if (null != sysMenuDtoList || sysMenuDtoList.size() > 0) {
            for (SysMenu sonSysMenu : sysMenuDtoList) {
                queryRoleDetail(sonSysMenu, roleId);
            }
        }
        return sysMenuDto;
    }

    @Override
    public List<SysMenu> getLoginUserMenuList(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        if (sysUserRoles == null) {
            return null;
        }
        Long[] roleIds = new Long[sysUserRoles.size()];
        for (SysUserRole sysUserRole : sysUserRoles) {
            roleIds = ArrayUtils.add(roleIds, sysUserRole.getRoleId());
        }

        if (ArrayUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<SysMenu> parentMenuList = null;
        //超级管理员
        Long roleId = roleIds[1];
        if (roleId == 1) {
            parentMenuList = sysRoleMenuService.getSuperAdminMenuList(null, roleId);
        } else {
            parentMenuList = sysRoleMenuService.getRoleMenuList(null, roleId);
        }

        List<SysMenu> menuList = new ArrayList<>();
        for (SysMenu parentMenu : parentMenuList) {
            parentMenu = iterateChildMenu(parentMenu, roleId);
            menuList.add(parentMenu);
        }
        return menuList;
    }

    private SysMenu iterateChildMenu(SysMenu parentMenu, Long roleId) {
        List<SysMenu> childMenuList = sysRoleMenuService.getRoleMenuList(parentMenu.getId(), roleId);
        parentMenu.setChildAuthorities(childMenuList);
        if (null != childMenuList || childMenuList.size() > 0) {
            for (SysMenu childMenu : childMenuList) {
                iterateChildMenu(childMenu, roleId);
            }
        }
        return parentMenu;
    }
}
