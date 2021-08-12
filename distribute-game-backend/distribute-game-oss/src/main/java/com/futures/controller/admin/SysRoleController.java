package com.futures.controller.admin;

import com.futures.model.dto.SysRoleDto;
import com.futures.model.entity.SysMenu;
import com.futures.model.entity.SysRole;
import com.futures.service.SysMenuFpi;
import com.futures.service.SysRoleFpi;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/role")
@Api(tags = "系统管理")
@Slf4j
public class SysRoleController {

    @Autowired
    private SysRoleFpi sysRoleFpi;
    @Autowired
    private SysMenuFpi sysMenuFpi;

    @ApiOperation(value = "【角色管理】-角色列表", notes = "角色管理-角色列表", response = SysRole.class)
    @PostMapping(value = "queryRole")
    public BaseResponse queryRole(Pageable page, @ApiParam(value = "角色名称") String roleName) {
        try {
            return BaseResponse.success(sysRoleFpi.queryRole(page.getPage(), roleName));
        } catch (BizException ye) {
            return BaseResponse.error(ye.getMessage());
        } catch (Exception e) {
            log.error("roleList error=" + e.getMessage(), e);
            return BaseResponse.error("查询角色失败");
        }
    }

    @ApiOperation(value = "【角色管理】-添加角色", notes = "角色管理-添加角色")
    @PostMapping(value = "addRole")
    @SysUserLog(module = "角色管理", action = "添加角色")
    public BaseResponse addRole(SysRoleDto sysRoleDto) {
        try {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(sysRoleDto, sysRole);
            sysRoleFpi.saveRole(sysRole);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error(sysRoleDto.getName() + " addRole error", e);
            return BaseResponse.error("添加角色失败");
        }
    }

    @ApiOperation(value = "【角色管理】-编辑角色", notes = "角色管理-编辑角色")
    @PostMapping(value = "modifyRole")
    @SysUserLog(module = "角色管理", action = "编辑角色")
    public BaseResponse modifyRole(SysRoleDto sysRoleDto) {
        if (null == sysRoleDto.getId()) {
            return BaseResponse.error("参数错误");
        }
        try {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(sysRoleDto, sysRole);
            sysRoleFpi.modifyRole(sysRole);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error(sysRoleDto.getId() + " editRole error=" + e.getMessage(), e);
            return BaseResponse.error("编辑角色失败");
        }
    }

    @ApiOperation(value = "【角色管理】-删除角色", notes = "角色管理-删除角色")
    @PostMapping(value = "deleteRoles")
    @SysUserLog(module = "角色管理", action = "删除角色")
    public BaseResponse deleteRoles(@ApiParam("角色ID")@RequestParam()Long[] roleIds) {
        if (ArrayUtils.isEmpty(roleIds)) {
            return BaseResponse.error("请选择要删除的角色");
        }
        try {
            sysRoleFpi.deleteRoles(roleIds);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("roleList error=" + e.getMessage(), e);
            return BaseResponse.error("删除角色失败");
        }
    }

    @ApiOperation(value = "【角色管理】-查看所有权限菜单", notes = "角色管理-查看所有权限菜单", response = SysMenu.class)
    @PostMapping(value = "getAllAuthorities")
    public BaseResponse getAllAuthorities() {
        try {
            return BaseResponse.success(sysMenuFpi.getMenuList());
        } catch (Exception e) {
            log.error("getMenuList error=" + e.getMessage(), e);
            return BaseResponse.error("查看所有权限菜单列表失败");
        }
    }

    @ApiOperation(value = "【角色管理】-分配角色权限", notes = "角色管理-分配角色权限")
    @PostMapping(value = "assignAuthority")
    @SysUserLog(module = "角色管理", action = "分配角色权限")
    public BaseResponse assignAuthority(@ApiParam(value = "角色ID")@RequestParam()Long roleId,
                                        @ApiParam("权限菜单ID")@RequestParam()Long[] menuIds) {
        if (null == roleId) {
            return BaseResponse.paramError("角色不能为空");
        }
        if (null == menuIds) {
            return BaseResponse.paramError("权限不能为空");
        }
        try {
            sysRoleFpi.assignAuthority(roleId, menuIds);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error(roleId + " assignAuthority error=" + e.getMessage(), e);
            return BaseResponse.error("分配角色权限失败");
        }
    }

    @ApiOperation(value = "【角色管理】-查看角色已分配的权限菜单", notes = "角色管理-查看角色已分配的权限菜单")
    @PostMapping(value = "getAuthorityByRole")
    public BaseResponse getAuthorityByRole(@ApiParam("角色ID")@RequestParam()Long roleId) {
        if (null == roleId) {
            return BaseResponse.paramError("角色ID不能为空");
        }
        try {
            return BaseResponse.success(sysMenuFpi.getRoleMenuList(roleId));
        } catch (Exception e) {
            log.error(roleId + "getRoleMenuList error=" + e.getMessage(), e);
            return BaseResponse.error("查看角色已分配的权限菜单失败");
        }
    }
}
