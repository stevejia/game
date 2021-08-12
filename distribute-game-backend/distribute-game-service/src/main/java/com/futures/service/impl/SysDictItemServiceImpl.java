package com.futures.service.impl;

import com.futures.mapper.SysDictItemMapper;
import com.futures.model.entity.SysDictItem;
import com.futures.service.SysDictItemService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDictItemServiceImpl extends CrudServiceSupport<SysDictItemMapper, SysDictItem> implements SysDictItemService {

}
