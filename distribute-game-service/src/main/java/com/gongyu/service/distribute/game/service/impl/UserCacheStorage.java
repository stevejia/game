package com.gongyu.service.distribute.game.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.gongyu.service.distribute.game.model.dto.SysMenuCacheDto;
import com.gongyu.service.distribute.game.model.dto.SysUserDto;
import com.gongyu.service.distribute.game.model.entity.SysMenu;
import com.gongyu.snowcloud.framework.web.constants.WebConstants;
import com.gongyu.snowcloud.framework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

public class UserCacheStorage {

    public static void store(SysUserDto sysUserDto, List<SysMenu> allAuthorities) {
        //缓存用户信息
        WebUtils.setCurrentUserId(sysUserDto.getId());
        WebUtils.setSessionAttribute(WebConstants.USER_PROFILE, JSONArray.toJSONString(sysUserDto));
        //缓存用户权限信息
        List<SysMenuCacheDto> sysMenuCacheDtoList = new ArrayList<>();
        for (SysMenu sysMenu : allAuthorities) {
            SysMenuCacheDto sysMenuCacheDto = new SysMenuCacheDto();
            sysMenuCacheDto.setId(sysMenu.getId());
            sysMenuCacheDto.setUrlPath(sysMenu.getUrlPath());
            sysMenuCacheDtoList.add(sysMenuCacheDto);
        }
        WebUtils.setSessionAttribute(WebConstants.USER_AUTHORITY, JSONArray.toJSONString(sysMenuCacheDtoList));
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static SysUserDto getCurrentUserProfile() {
        Object profile = WebUtils.getSessionAttribute(WebConstants.USER_PROFILE);
        if (null == profile) {
            return new SysUserDto();
        }
        return JSONArray.parseObject(profile.toString(), SysUserDto.class);
    }

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static Long getCurrentUserId() {
        return WebUtils.getSessionAttribute(WebConstants.USER_ID);
    }

}
