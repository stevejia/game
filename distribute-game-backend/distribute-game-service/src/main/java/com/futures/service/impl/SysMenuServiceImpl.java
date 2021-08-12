package com.futures.service.impl;

import com.futures.mapper.SysMenuMapper;
import com.futures.model.entity.SysMenu;
import com.futures.service.SysMenuService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl extends CrudServiceSupport<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public String getParentList(Long menuId) {
        return sysMenuMapper.getParentList(menuId);
    }

}
