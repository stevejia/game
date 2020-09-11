package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysUser;
import com.gongyu.service.distribute.game.model.entity.SysUserLog;
import com.gongyu.service.distribute.game.model.entity.SysUserRole;
import com.gongyu.service.distribute.game.service.SysUserFpi;
import com.gongyu.service.distribute.game.service.SysUserLogService;
import com.gongyu.service.distribute.game.service.SysUserRoleService;
import com.gongyu.service.distribute.game.service.SysUserService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.util.MD5;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SysUserFpiImpl implements SysUserFpi {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserLogService sysUserLogService;

    @Override
    public SysUser login(String username, String password) throws BizException {
        SysUser sysUser = getSysUserByUsername(username);
        if (null == sysUser) {
            throw new BizException("该账号不存在");
        }
        if (!StringUtils.equalsIgnoreCase(password, sysUser.getPassword())) {
            throw new BizException("密码不正确");
        }
        if (sysUser.getIsEnabled() == 0) {
            throw new BizException("该账号已被禁用");
        }
        if (sysUser.getIsLocked() == 1) {
            throw new BizException("该账号已被锁定");
        }
        sysUser.setLoginIp(WebUtils.getClientIp());
        sysUser.setUserAgent(WebUtils.getRequest().getHeader("User-Agent"));
        sysUser.setLoginSessionKey(WebUtils.getSessionId());
        sysUser.setLoginDate(new Date());
        sysUser.setLoginFailureCount(sysUser.getLoginFailureCount() + 1);
        sysUserService.updateById(sysUser);

        SysUserRole sysUserRole = sysUserRoleService.getOne(new QueryWrapper<SysUserRole>().eq("user_id",sysUser.getId()));
        sysUser.setRoleId(null==sysUserRole?null:sysUserRole.getRoleId());
        return sysUser;
    }

    private SysUser getSysUserByUsername(String username) {
        return sysUserService.getOne(new QueryWrapper<SysUser>().eq("deleted",0).eq("username",username));
    }


    @Override
    @Transactional(rollbackFor = {BizException.class,Exception.class, RuntimeException.class})
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            throw new BizException("旧密码不能为空");
        }
        if (StringUtils.isEmpty(newPassword)) {
            throw new BizException("新密码不能为空");
        }
        SysUser sysUser = sysUserService.getById(userId);
        if (null == sysUser) {
            throw new BizException("账号不存在");
        }
        if (!StringUtils.equals(oldPassword, sysUser.getPassword())) {
            throw new BizException("原密码不正确");
        }
        sysUser.setPassword(newPassword);
        sysUserService.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class,Exception.class, RuntimeException.class})
    public void changeTwoLevelPassword(Long userId, String oldPassword, String newPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            throw new BizException("旧密码不能为空");
        }
        if (StringUtils.isEmpty(newPassword)) {
            throw new BizException("新密码不能为空");
        }
        SysUser sysUser = sysUserService.getById(userId);
        if (null == sysUser) {
            throw new BizException("账号不存在");
        }
        if (!StringUtils.equals(oldPassword, sysUser.getTwoLevelPassword())) {
            throw new BizException("原密码不正确");
        }
        sysUser.setTwoLevelPassword(newPassword);
        sysUserService.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void createSysUser(SysUser sysUser, Long roleId){
        if (StringUtils.isBlank(sysUser.getUsername())) {
            throw new BizException("请输入用户名");
        }
        String username_regex = "^[a-zA-Z0-9]{6,20}$";
        if (!Pattern.compile(username_regex).matcher(sysUser.getUsername()).matches()) {
            throw new BizException("用户名只能是英文字符和数字，长度6到20");
        }
        if (StringUtils.isBlank(sysUser.getName())) {
            throw new BizException("请输入姓名");
        }
        if (null == roleId) {
            throw new BizException("请选择角色");
        }
        SysUser sysUserTemp = getSysUserByUsername(sysUser.getUsername());
        if (null != sysUserTemp) {
            throw new BizException("账号：" + sysUser.getUsername() + "已存在");
        }
        sysUser.setIsEnabled(1);
        sysUser.setIsLocked(0);
        sysUser.setPhone(sysUser.getPhone());
        sysUser.setPassword(MD5.string2MD5(sysUser.getPassword()));
        sysUser.setTwoLevelPassword(MD5.string2MD5(sysUser.getTwoLevelPassword()));
        sysUserService.save(sysUser);

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(roleId);
        sysUserRoleService.save(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteSysUser(String[] userIds){
        for (String userId : userIds) {
            sysUserService.removeById(Long.parseLong(userId));
        }
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void resetPassword(Long userId, String newPwd) {
        if (StringUtils.isEmpty(newPwd)) {
            throw new BizException("新密码不能为空");
        }
        SysUser sysUser = sysUserService.getById(userId);
        if (null == sysUser) {
            throw new BizException("账号不存在");
        }
        sysUser.setPassword(MD5.string2MD5(newPwd));
        sysUserService.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void resetTwoLevelPassword(Long userId, String newPwd) {
        if (StringUtils.isEmpty(newPwd)) {
            throw new BizException("新二级密码不能为空");
        }
        SysUser sysUser = sysUserService.getById(userId);
        if (null == sysUser) {
            throw new BizException("账号不存在");
        }
        sysUser.setTwoLevelPassword(MD5.string2MD5(newPwd));
        sysUserService.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void updateSysUser(SysUser sysUser, Long roleId) {
        if (sysUser.getId() == null || !sysUserService.exists(sysUser.getId())) {
            throw new BizException("账号不存在");
        }
        sysUserService.updateById(sysUser);

        if (null != roleId) {
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", sysUser.getId()));
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);
        }
    }

    @Override
    public IPage<SysUser> querySysUser(IPage page, String name, String username, Long roleId, Long deptId) {
        return sysUserService.querySysUser(page, name, username, roleId, deptId);
    }

    @Override
    public List<String> queryUserLogModes() {
        return sysUserLogService.queryUserLogModes();
    }

    @Override
    public IPage<SysUserLog> querySysUserLog(IPage<SysUserLog> page, String userName, String modelName, String argument, String startTime, String endTime) {
        QueryWrapper<SysUserLog> sysUserLogWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userName)){
            sysUserLogWrapper = sysUserLogWrapper.eq("user_name",userName);
        }
        if(!StringUtils.isEmpty(modelName)){
            sysUserLogWrapper = sysUserLogWrapper.eq("model_name",modelName);
        }
        if(!StringUtils.isEmpty(argument)){
            sysUserLogWrapper = sysUserLogWrapper.like("argument",argument);
        }
        if(!StringUtils.isEmpty(startTime)){
            sysUserLogWrapper = sysUserLogWrapper.ge("operation_time", startTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            sysUserLogWrapper = sysUserLogWrapper.le("operation_time", endTime);
        }
        sysUserLogWrapper = sysUserLogWrapper.orderByDesc("operation_time");
        return sysUserLogService.page(page, sysUserLogWrapper);
    }

    @Override
    public List<SysUserLog> exportSysUserLog(String userName, String modelName, String argument, String startTime, String endTime) {
        QueryWrapper<SysUserLog> sysUserLogWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userName)){
            sysUserLogWrapper = sysUserLogWrapper.like("user_name",userName);
        }
        if(!StringUtils.isEmpty(modelName)){
            sysUserLogWrapper = sysUserLogWrapper.eq("model_name",modelName);
        }
        if(!StringUtils.isEmpty(argument)){
            sysUserLogWrapper = sysUserLogWrapper.like("argument",argument);
        }
        if(!StringUtils.isEmpty(startTime)){
            sysUserLogWrapper = sysUserLogWrapper.ge("operation_time", startTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            sysUserLogWrapper = sysUserLogWrapper.le("operation_time", endTime);
        }
        sysUserLogWrapper = sysUserLogWrapper.orderByDesc("operation_time");
        return sysUserLogService.list(sysUserLogWrapper);
    }
}
