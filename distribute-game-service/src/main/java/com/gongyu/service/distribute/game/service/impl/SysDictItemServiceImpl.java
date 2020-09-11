package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysDictItemMapper;
import com.gongyu.service.distribute.game.model.entity.SysDictItem;
import com.gongyu.service.distribute.game.service.SysDictItemService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.stereotype.Service;

@Service
public class SysDictItemServiceImpl extends CrudServiceSupport<SysDictItemMapper, SysDictItem> implements SysDictItemService {

}
