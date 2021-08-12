package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.entity.SysUser;
import com.futures.model.entity.SysUserLog;

import java.util.List;

public interface SysUserFpi {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    SysUser login(String username, String password);

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    void changePassword(Long userId, String oldPwd, String newPwd);

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    void changeTwoLevelPassword(Long userId, String oldPwd, String newPwd);

    /**
     * 重置密码
     * @param userId
     * @param newPwd
     */
    void resetPassword(Long userId, String newPwd);

    /**
     * 重置二级密码
     * @param userId
     * @param newPwd
     */
    void resetTwoLevelPassword(Long userId, String newPwd);

    /**
     * 删除人员
     * @param userIds
     */
    void deleteSysUser(String[] userIds);

    /**
     * 添加人员
     * @param sysUser
     * @param roleId
     */
    void createSysUser(SysUser sysUser, Long roleId);

    /**
     * 修改人员
     * @param sysUser
     * @param roleId
     */
    void updateSysUser(SysUser sysUser, Long roleId);

    /**
     * 人员列表综合查询
     * @param page
     * @param name
     * @param username
     * @param roleId
     * @return
     */
    IPage<SysUser> querySysUser(IPage page, String name, String username, Long roleId, Long deptId);

    /**
     * 查询日志操作模块
     * @return
     */
    List<String> queryUserLogModes();

    /**
     * 查询所有操作日志
     * @param page
     * @param userName
     * @param modelName
     * @param argument
     * @param startTime
     * @param endTime
     * @return
     */
    IPage<SysUserLog> querySysUserLog(IPage<SysUserLog> page, String userName, String modelName, String argument, String startTime, String endTime);

    /**
     * 导出所有操作日志
     * @param userName
     * @param modelName
     * @param argument
     * @param startTime
     * @param endTime
     * @return
     */
    List<SysUserLog> exportSysUserLog(String userName, String modelName, String argument, String startTime, String endTime);

}
