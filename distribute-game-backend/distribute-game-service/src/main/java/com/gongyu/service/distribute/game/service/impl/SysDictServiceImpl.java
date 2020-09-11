package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysDictMapper;
import com.gongyu.service.distribute.game.model.entity.SysDict;
import com.gongyu.service.distribute.game.service.SysDictService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends CrudServiceSupport<SysDictMapper, SysDict> implements SysDictService {

}
