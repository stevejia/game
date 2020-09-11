package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.dto.SysBtnDto;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.service.distribute.game.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysMenu> getRoleDetail(@Param("parent") Long parent, @Param("roleId") Long roleId);

    List<SysMenu> getSuperAdminMenuList(@Param("parent") Long parent, @Param("roleId") Long roleId);

    List<SysMenu> getRoleMenuList(@Param("parent") Long parent, @Param("roleId") Long roleId);

    List<SysMenu> getUserMenuList(@Param("userId") Long userId);

    List<SysBtnDto> getBtnList(@Param("roleId") Long roleId);
}