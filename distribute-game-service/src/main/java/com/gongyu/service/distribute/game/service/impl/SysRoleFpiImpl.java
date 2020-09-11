package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysRole;
import com.gongyu.service.distribute.game.model.entity.SysRoleMenu;
import com.gongyu.service.distribute.game.model.entity.SysUserRole;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class SysRoleFpiImpl implements SysRoleFpi {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public IPage<SysRole> queryRole(IPage page, String roleName) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper = sysRoleQueryWrapper.eq("deleted",0).eq("is_system",0);
        if(!StringUtils.isEmpty(roleName)){
            sysRoleQueryWrapper = sysRoleQueryWrapper.eq("name",roleName);
        }
        sysRoleQueryWrapper = sysRoleQueryWrapper.orderByDesc("createDate");
        return sysRoleService.page(page, sysRoleQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void saveRole(SysRole sysRole) {
        if(StringUtils.isEmpty(sysRole.getName())){
            throw new BizException("角色名称不能为空");
        }
        if (sysRoleService.exists("name", sysRole.getName())) {
            throw new BizException("不允许重复添加角色");
        }
        sysRoleService.save(sysRole);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void modifyRole(SysRole sysRole) {
        if(sysRoleService.exists(new QueryWrapper<SysRole>().eq("deleted",0).eq("name", sysRole.getName()).ne("id",sysRole.getId()))) {
            throw new BizException("已存在该角色，不能重复添加");
        }
        sysRoleService.updateById(sysRole);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void assignAuthority(Long roleId, Long[] menuIds) {
        if(null==menuIds||menuIds.length<=0){
            throw new BizException("分配的权限菜单为空");
        }
        //删除老关系
        sysRoleMenuService.deleteRoleMenu(roleId);
        //建立新关系
        for (Long menuId : menuIds) {
            String allMenus = sysMenuService.getParentList(menuId);
            String[] menus = allMenus.split(",");
            for(String menu : menus){
                List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId).eq("menu_id",Long.parseLong(menu)));
                if(null==sysRoleMenuList||sysRoleMenuList.size()==0){
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(Long.parseLong(menu));
                    sysRoleMenuService.save(sysRoleMenu);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void deleteRoles(Long[] roleIds) {
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("deleted", 0).eq("role_id", roleIds[0]));
        if(null!=sysUserRoleList && sysUserRoleList.size()>0){
            throw new BizException("该角色下还有人员，不能删除");
        }
        sysRoleService.removeByIds(Arrays.asList(roleIds));
    }
}
