package com.gongyu.application.distribute.game.controller.admin;

import com.gongyu.service.distribute.game.model.dto.SysBtnDto;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.service.distribute.game.service.SysMenuFpi;
import com.gongyu.service.distribute.game.service.impl.UserCacheStorage;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth/menu")
@Api(tags = "系统管理")
@Slf4j
public class SysMenuController {
    @Autowired
    private SysMenuFpi sysMenuFpi;

    @ApiOperation(value = "【权限管理】-系统菜单列表", notes = "权限管理-系统菜单列表", response = SysMenu.class, responseContainer = "List")
    @PostMapping(value = "list")
    public BaseResponse menuList() {
        try {
            Map<String,Object> map = new HashMap<>();
            List<SysMenu> menuList = sysMenuFpi.getLoginUserMenuList(UserCacheStorage.getCurrentUserId());
            Map<String,Boolean> btnList = new HashMap<>();
            List<SysBtnDto> sysBtnDtoList = sysMenuFpi.getBtnList(UserCacheStorage.getCurrentUserProfile().getRoleId());
            for(SysBtnDto sysBtnDto:sysBtnDtoList){
                btnList.put(sysBtnDto.getBtnName(), 1==sysBtnDto.getIsSelect());
            }
            map.put("menuList", menuList);
            map.put("btnList", btnList);
            return BaseResponse.success(map);
        } catch (Exception e) {
            log.error(" menuList error=" + e.getMessage(), e);
        }
        return BaseResponse.success();
    }

}
