package com.futures.controller.admin;

import com.futures.model.dto.SysUserDto;
import com.futures.model.dto.SysValidCodeDto;
import com.futures.model.entity.SysMenu;
import com.futures.model.entity.SysUser;
import com.futures.service.SysMenuFpi;
import com.futures.service.SysUserFpi;
import com.futures.service.impl.UserCacheStorage;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import com.gongyu.snowcloud.framework.web.constants.WebConstants;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("auth")
@Api(tags = "系统管理")
@Slf4j
public class LoginController {
    @Autowired
    private SysUserFpi sysUserFpi;
    @Autowired
    private SysMenuFpi sysMenuFpi;

    @ApiOperation(value = "【权限管理】-登录", notes = "权限管理-登录")
    @PostMapping(value = "login")
    @SysUserLog(module = "管理员", action = "登录")
    public BaseResponse login(@ApiParam("用户名") String username,
                              @ApiParam("密码") String password,
                              @ApiParam("验证码Key") String codeKey,
                              @ApiParam("验证码") String code) {
        if (StringUtils.isEmpty(username)) {
            return BaseResponse.paramError("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return BaseResponse.paramError("密码不能为空");
        }
        if (StringUtils.isEmpty(codeKey) || StringUtils.isEmpty(code)) {
            return BaseResponse.paramError("验证码不能为空");
        }
        String codeRedis = RedisUtils.get(codeKey);
        if (StringUtils.isEmpty(codeRedis)) {
            return BaseResponse.paramError("验证码失效");
        }
        if (!StringUtils.equals(code, codeRedis)) {
            return BaseResponse.paramError("验证码错误");
        }
        try {
            SysUser sysUser = sysUserFpi.login(username, password);
            List<SysMenu> allAuthorities = sysMenuFpi.getUserMenuList(sysUser.getId());
            SysUserDto sysUserDto = new SysUserDto();
            BeanUtils.copyProperties(sysUser, sysUserDto);
            sysUserDto.setKey(WebUtils.getSessionId());
            UserCacheStorage.store(sysUserDto, allAuthorities);
            return BaseResponse.success(sysUserDto);
        } catch (BizException ye) {
            return BaseResponse.error(ye.getMessage());
        } catch (Exception e) {
            log.error(username + " login error:" + e.getMessage(), e);
            return BaseResponse.error("登录失败");
        }
    }

    @ApiOperation(value = "【权限管理】-退出", notes = "权限管理-退出")
    @PostMapping(value = "logout")
    @SysUserLog(module = "管理员", action = "退出")
    public BaseResponse logout() {
        try {
            WebUtils.removeSessionAttribute(WebConstants.USER_ID);
            return BaseResponse.success();
        } catch (Exception e) {
            log.error(UserCacheStorage.getCurrentUserProfile().getUsername() + " logout error=" + e.getMessage(), e);
            return BaseResponse.success();
        }
    }

    @ApiOperation(value = "【权限管理】-获取登录验证码", notes = "权限管理-获取登录验证码")
    @PostMapping(value = "getLoginCode")
    public BaseResponse getLoginCode() {
        String value = RandomStringUtils.randomNumeric(4);
        String uuid = value + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        try {
            SysValidCodeDto loginCode = new SysValidCodeDto();
            loginCode.setCodeKey(uuid);
            loginCode.setCodeValue(value);
            RedisUtils.set(uuid, value, 180L);
            return BaseResponse.success(loginCode);
        } catch (Exception e) {
            log.error("getLoginCode error=" + e.getMessage(), e);
            return BaseResponse.error();
        }
    }

    @ApiOperation(value = "【权限管理】-修改密码", notes = "权限管理-修改密码")
    @PostMapping(value = "changePassword")
    @SysUserLog(module = "管理员", action = "修改密码")
    public BaseResponse changePassword(@ApiParam("原密码") String oldPwd, @ApiParam("新密码") String newPwd) {
        try {
            sysUserFpi.changePassword(UserCacheStorage.getCurrentUserId(), oldPwd, newPwd);
            return BaseResponse.success();
        } catch (BizException he) {
            return BaseResponse.bussinessError(he.getMessage());
        } catch (Exception e) {
            log.error(UserCacheStorage.getCurrentUserId() + " changePassword error=" + e.getMessage(), e);
            return BaseResponse.error("修改密码失败");
        }
    }

    @ApiOperation(value = "【权限管理】-修改二级密码", notes = "权限管理-修改二级密码")
    @PostMapping(value = "changeTwoLevelPassword")
    @SysUserLog(module = "管理员", action = "修改二级密码")
    public BaseResponse changeTwoLevelPassword(@ApiParam("原密码") String oldPwd, @ApiParam("新密码") String newPwd) {
        try {
            sysUserFpi.changeTwoLevelPassword(UserCacheStorage.getCurrentUserId(), oldPwd, newPwd);
            return BaseResponse.success();
        } catch (BizException he) {
            return BaseResponse.bussinessError(he.getMessage());
        } catch (Exception e) {
            log.error(UserCacheStorage.getCurrentUserId() + " changePassword error=" + e.getMessage(), e);
            return BaseResponse.error("修改二级密码失败");
        }
    }
}
