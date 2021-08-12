package com.futures.controller.admin;

import com.futures.model.dto.SysUserDto;
import com.futures.model.entity.SysUser;
import com.futures.service.SysUserFpi;
import com.futures.service.impl.UserCacheStorage;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
@Api(tags = "系统管理")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserFpi sysUserFpi;

    @ApiOperation(value = "【人员管理】-人员列表", notes = "人员管理-人员列表")
    @PostMapping(value = "querySysUser")
    public BaseResponse querySysUser(Pageable page,
                                      @ApiParam("姓名")String name,
                                      @ApiParam("用户名")String username,
                                      @ApiParam("角色ID")Long roleId,
                                      @ApiParam("部门ID")Long deptId) {
        try {
            return BaseResponse.success(sysUserFpi.querySysUser(page.getPage(), name, username, roleId, deptId));
        } catch (Exception e) {
            log.error("querySysUser error=" + e.getMessage(), e);
            return BaseResponse.error("查询人员列表失败");
        }
    }

    @ApiOperation(value = "【人员管理】-添加人员", notes = "人员管理-添加人员")
    @PostMapping(value = "createSysUser")
    @SysUserLog(module = "人员管理", action = "添加人员")
    public BaseResponse createSysUser(SysUserDto sysUserDto) {
        try {
            sysUserDto.setId(UserCacheStorage.getCurrentUserId());
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(sysUserDto, sysUser);
            sysUser.setTwoLevelPassword("123456");
            sysUserFpi.createSysUser(sysUser, sysUserDto.getRoleId());
            return BaseResponse.success();
        } catch (BizException ye) {
            return BaseResponse.error(ye.getMessage());
        } catch (Exception e) {
            log.error("createSysUser error=" + e.getMessage(), e);
            return BaseResponse.error("添加人员失败");
        }
    }

    @ApiOperation(value = "【人员管理】-编辑人员", notes = "人员管理-编辑人员")
    @PostMapping(value = "updateSysUser")
    @SysUserLog(module = "人员管理", action = "编辑人员")
    public BaseResponse updateSysUser(SysUserDto sysUserDto) {
        try {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(sysUserDto, sysUser);
            sysUserFpi.updateSysUser(sysUser, sysUserDto.getRoleId());
            return BaseResponse.success();
        } catch (BizException he) {
            return BaseResponse.error(he.getMessage());
        } catch (Exception e) {
            log.error(sysUserDto.getId() + " updateSysUser error=" + e.getMessage(), e);
            return BaseResponse.error("编辑人员失败");
        }
    }

    @ApiOperation(value = "【人员管理】-重置密码", notes = "人员管理-重置密码")
    @PostMapping(value = "resetPassword")
    @SysUserLog(module = "人员管理", action = "重置密码")
    public BaseResponse resetPassword(@ApiParam("运营人员ID")Long userId, @ApiParam("新密码")String newPwd) {
        try {
            sysUserFpi.resetPassword(userId, newPwd);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.bussinessError(e.getMessage());
        } catch (Exception e) {
            log.error(UserCacheStorage.getCurrentUserId() + " changePassword error=" + e.getMessage(), e);
            return BaseResponse.error("修改密码失败");
        }
    }

    @ApiOperation(value = "【人员管理】-重置二级密码", notes = "人员管理-重置二级密码")
    @PostMapping(value = "resetTwoLevelPassword")
    @SysUserLog(module = "人员管理", action = "重置密码")
    public BaseResponse resetTwoLevelPassword(@ApiParam("运营人员ID")Long userId, @ApiParam("新密码")String newPwd) {
        try {
            sysUserFpi.resetPassword(userId, newPwd);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.bussinessError(e.getMessage());
        } catch (Exception e) {
            log.error(UserCacheStorage.getCurrentUserId() + " changePassword error=" + e.getMessage(), e);
            return BaseResponse.error("修改密码失败");
        }
    }

    @ApiOperation(value = "【人员管理】-删除人员", notes = "人员管理-删除人员")
    @PostMapping(value = "deleteSysUser")
    @SysUserLog(module = "人员管理", action = "删除人员")
    public BaseResponse deleteSysUser(@ApiParam(value = "人员ID列表,以逗号分隔", required = true) @RequestParam() String userIds) {
        try {
            if (StringUtils.isBlank(userIds)) {
                throw new BizException("参数不能为空");
            }
            sysUserFpi.deleteSysUser(userIds.split(","));
            return BaseResponse.success();
        } catch (BizException he) {
            return BaseResponse.error(he.getMessage());
        } catch (Exception e) {
            log.error(userIds + " deleteSysUser error=" + e.getMessage(), e);
            return BaseResponse.error("删除人员失败");
        }
    }
}
