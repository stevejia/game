package com.gongyu.service.distribute.game.service;

import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;
import org.apache.ibatis.annotations.Param;

public interface SysMenuService extends CrudService<SysMenu> {
    /**
     * 查询所有父级权限菜单，逗号隔开
     * @param menuId
     * @return
     */
    String getParentList(@Param("menuId") Long menuId);
}
