package com.futures.service.impl;

import com.futures.mapper.SysDeptMapper;
import com.futures.model.entity.SysDept;
import com.futures.service.SysDeptService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl extends CrudServiceSupport<SysDeptMapper, SysDept> implements SysDeptService {

}

