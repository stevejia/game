package com.gongyu.service.distribute.game.service.impl;

import com.gongyu.service.distribute.game.mapper.SysMenuMapper;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.service.distribute.game.service.SysMenuService;
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
