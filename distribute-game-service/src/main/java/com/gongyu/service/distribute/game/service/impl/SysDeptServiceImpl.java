package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysDeptMapper;
import com.gongyu.service.distribute.game.model.entity.SysDept;
import com.gongyu.service.distribute.game.service.SysDeptService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl extends CrudServiceSupport<SysDeptMapper, SysDept> implements SysDeptService {

}

