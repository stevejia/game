package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysUser;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SysUserService extends CrudService<SysUser> {
    /**
     * 人员列表
     * @param page
     * @param name
     * @param username
     * @param roleId
     * @param deptId
     * @return
     */
    IPage<SysUser> querySysUser(IPage page, String name, String username, Long roleId, Long deptId);

}
