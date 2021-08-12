package com.futures.service.impl;

import com.futures.mapper.SysDictMapper;
import com.futures.model.entity.SysDict;
import com.futures.service.SysDictService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends CrudServiceSupport<SysDictMapper, SysDict> implements SysDictService {

}
