package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futures.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getUserMenuList(Long userId);

    String getParentList(@Param("menuId") Long menuId);

}