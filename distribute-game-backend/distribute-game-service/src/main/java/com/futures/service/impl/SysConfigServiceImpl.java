package com.futures.service.impl;

import com.futures.mapper.SysConfigMapper;
import com.futures.model.entity.SysConfig;
import com.futures.service.SysConfigService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends CrudServiceSupport<SysConfigMapper, SysConfig> implements SysConfigService {

}
