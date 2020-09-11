package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysUserLogMapper;
import com.gongyu.service.distribute.game.model.entity.SysUserLog;
import com.gongyu.service.distribute.game.service.SysUserLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserLogServiceImpl extends CrudServiceSupport<SysUserLogMapper, SysUserLog> implements SysUserLogService {
    @Autowired
    private SysUserLogMapper sysUserLogMapper;

    @Override
    public List<String> queryUserLogModes() {
        return sysUserLogMapper.queryUserLogModes();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveSysUserLog(SysUserLog sysUserLog) {
        sysUserLogMapper.saveSysUserLog(sysUserLog);
    }
}