package com.futures.service;

import com.futures.model.dto.SysBtnDto;
import com.futures.model.entity.SysMenu;
import com.futures.model.entity.SysRoleMenu;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;

public interface SysRoleMenuService extends CrudService<SysRoleMenu> {
    /**
     * 查询角色详情
     * @param parent
     * @param roleId
     * @return
     */
    List<SysMenu> getRoleDetail(Long parent, Long roleId);

    /**
     * 查询超级管理员菜单
     * @param parent
     * @param roleId
     * @return
     */
    List<SysMenu> getSuperAdminMenuList(Long parent, Long roleId);

    /**
     * 查询角色菜单权限
     * @param parent
     * @param roleId
     * @return
     */
    List<SysMenu> getRoleMenuList(Long parent, Long roleId);

    /**
     * 查询用户菜单权限
     * @param userId
     * @return
     */
    List<SysMenu> getUserMenuList(Long userId);

    /**
     * 查询角色所有按钮权限
     * @param roleId
     * @return
     */
    List<SysBtnDto> getBtnList(Long roleId);

    /**
     * 删除角色菜单权限
     * @param roleId
     */
    void deleteRoleMenu(Long roleId);

}
