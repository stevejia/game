package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysAreaMapper;
import com.gongyu.service.distribute.game.model.dto.SysAreaLetterDto;
import com.gongyu.service.distribute.game.model.entity.SysArea;
import com.gongyu.service.distribute.game.service.SysAreaService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAreaServiceImpl extends CrudServiceSupport<SysAreaMapper, SysArea> implements SysAreaService {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Override
    public List<SysAreaLetterDto> queryCityByLetterOrder() {
        return sysAreaMapper.queryCityByLetterOrder();
    }
}
