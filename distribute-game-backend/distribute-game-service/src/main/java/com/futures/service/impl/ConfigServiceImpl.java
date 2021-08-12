package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.ConfigMapper;
import com.futures.model.dto.ConfigModifyDto;
import com.futures.model.dto.ConfigSaveDto;
import com.futures.model.entity.Config;
import com.futures.service.ConfigService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigServiceImpl extends CrudServiceSupport<ConfigMapper, Config> implements ConfigService {

    @Override
    public IPage<Config> queryConfig(IPage<Config> page, Integer configGroup) {
        return this.page(page, Wrappers.<Config>lambdaQuery().eq(Config::getConfigGroup, configGroup).orderByAsc(Config::getSort));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveConfig(ConfigSaveDto configSaveDto) {
        Config config = new Config();
        BeanUtils.copyProperties(configSaveDto, config);
        config.setUpdateTime(DateUtil.getNowDate());
        config.setCreateTime(DateUtil.getNowDate());
        this.save(config);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyConfig(ConfigModifyDto configModifyDto) {
        Config config = new Config();
        BeanUtils.copyProperties(configModifyDto, config);
        config.setUpdateTime(DateUtil.getNowDate());
        this.updateById(config);
    }
}