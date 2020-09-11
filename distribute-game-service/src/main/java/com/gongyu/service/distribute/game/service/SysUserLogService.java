package com.gongyu.service.distribute.game.service;

import com.gongyu.service.distribute.game.model.entity.SysUserLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;

public interface SysUserLogService extends CrudService<SysUserLog> {
    /**
     * 查询日志操作模块
     * @return
     */
    List<String> queryUserLogModes();

    /**
     * 保存操作日志
     * @param sysUserLog
     */
    void saveSysUserLog(SysUserLog sysUserLog);
}