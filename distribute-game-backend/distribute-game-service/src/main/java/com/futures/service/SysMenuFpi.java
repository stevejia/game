package com.futures.service;


import com.futures.model.dto.SysBtnDto;
import com.futures.model.entity.SysMenu;

import java.util.List;

public interface SysMenuFpi {
    /**
     * 查询所有权限菜单
     * @return
     */
    List<SysMenu> getMenuList();

    /**
     * 查询所有权限按钮
     * @return
     */
    List<SysBtnDto> getBtnList(Long roleId);

    /**
     * 获取用户所有权限菜单
     * @param userId
     * @return
     */
    List<SysMenu> getUserMenuList(Long userId);

    /**
     * 获取角色所有权限菜单
     * @param roleId
     * @return
     */
    List<SysMenu> getRoleMenuList(Long roleId);

    /**
     * 获取当前登录用户所有权限菜单
     * @param userId
     * @return
     */
    List<SysMenu> getLoginUserMenuList(Long userId);
}
