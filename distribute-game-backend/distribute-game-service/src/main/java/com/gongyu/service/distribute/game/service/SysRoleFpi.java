package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysRole;
import com.gongyu.snowcloud.framework.base.exception.BizException;

public interface SysRoleFpi {
    /**
     * 查询角色
     * @param page
     * @param roleName
     * @return
     */
    IPage<SysRole> queryRole(IPage page, String roleName);

    /**
     * 添加角色
     * @param sysRole
     */
    void saveRole(SysRole sysRole);

    /**
     * 编辑角色
     * @param sysRoleDto
     */
    void modifyRole(SysRole sysRoleDto);

    /**
     * 分配权限
     * @param roleId
     * @param menuIds
     */
    void assignAuthority(Long roleId, Long[] menuIds);

    /**
     * 删除角色
     * @param roleIds
     */
    void deleteRoles(Long[] roleIds) throws BizException;
}
