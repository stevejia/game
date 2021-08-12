package com.futures.service.impl;

import com.futures.mapper.SysAreaMapper;
import com.futures.model.dto.SysAreaLetterDto;
import com.futures.model.entity.SysArea;
import com.futures.service.SysAreaService;
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
