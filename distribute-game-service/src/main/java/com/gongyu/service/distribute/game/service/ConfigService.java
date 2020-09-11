package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.ConfigModifyDto;
import com.gongyu.service.distribute.game.model.dto.ConfigSaveDto;
import com.gongyu.service.distribute.game.model.entity.Config;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ConfigService extends CrudService<Config> {

    IPage<Config> queryConfig(IPage<Config> page, Integer configGroup);

    void saveConfig(ConfigSaveDto configSaveDto);

    void modifyConfig(ConfigModifyDto configModifyDto);
}