package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysConfig;
import com.gongyu.service.distribute.game.service.SysConfigFpi;
import com.gongyu.service.distribute.game.service.SysConfigService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SysConfigFpiImpl implements SysConfigFpi {
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public String selectByKey(String configKey) {
        QueryWrapper<SysConfig> sysConfigWrapper = new QueryWrapper<>();
        sysConfigWrapper = sysConfigWrapper.eq("config_key", configKey);
        SysConfig sysConfig = sysConfigService.getOne(sysConfigWrapper);
        if(null==sysConfig){
            throw new BizException("配置项【" + configKey + "】未配置");
        }
        return sysConfig.getConfigValue();
    }

    @Override
    public SysConfig getSysConfig(String configKey) {
        QueryWrapper<SysConfig> sysConfigWrapper = new QueryWrapper<>();
        sysConfigWrapper = sysConfigWrapper.eq("config_key", configKey);
        SysConfig sysConfig = sysConfigService.getOne(sysConfigWrapper);
        if(null==sysConfig){
            throw new BizException("配置项【" + configKey + "】未配置");
        }
        return sysConfig;
    }

    @Override
    public IPage<SysConfig> querySysConfig(IPage<SysConfig> page, String configKey) {
        QueryWrapper<SysConfig> sysConfigWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(configKey)){
            sysConfigWrapper = sysConfigWrapper.eq("config_key",configKey);
        }
        sysConfigWrapper = sysConfigWrapper.orderByDesc("createDate");
        return sysConfigService.page(page, sysConfigWrapper);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void saveSysConfig(String configKey, String configValue, String remark) {
        if(sysConfigService.exists("config_key",configKey)){
            throw new BizException("配置项【" + configKey + "】已存在");
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigKey(configKey);
        sysConfig.setConfigValue(configValue);
        sysConfig.setRemark(remark);
        sysConfig.setConfigKey(configKey);
        sysConfigService.save(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void modifySysConfig(Long id, String configKey, String configValue, String remark) {
        SysConfig sysConfig = sysConfigService.getById(id);
        if(null==sysConfig){
            throw new BizException("该配置不存在");
        }
        if(sysConfigService.exists(new QueryWrapper<SysConfig>().eq("deleted",0).eq("config_key",configKey).ne("id",id))) {
            throw new BizException("配置项【" + configKey + "】已存在");
        }
        sysConfig.setConfigKey(configKey);
        sysConfig.setConfigValue(configValue);
        sysConfig.setRemark(remark);
        sysConfigService.updateById(sysConfig);
    }

    @Override
    public void deleteSysConfig(Long id) {
        sysConfigService.removeById(id);
    }
}
