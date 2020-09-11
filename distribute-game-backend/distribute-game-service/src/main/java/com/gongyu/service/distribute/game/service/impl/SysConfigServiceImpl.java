package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysConfigMapper;
import com.gongyu.service.distribute.game.model.entity.SysConfig;
import com.gongyu.service.distribute.game.service.SysConfigService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends CrudServiceSupport<SysConfigMapper, SysConfig> implements SysConfigService {

}
