package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.SysConfig;

public interface SysConfigFpi {
    /**
     * 查看系统配置值
     * @param configKey
     * @return
     */
    String selectByKey(String configKey);

    /**
     * String configKey
     * @param configKey
     * @return
     */
    SysConfig getSysConfig(String configKey);

    /**
     * 系统配置列表
     * @param page
     * @param configKey
     * @return
     */
    IPage<SysConfig> querySysConfig(IPage<SysConfig> page, String configKey);

    /**
     * 添加系统配置
     * @param configKey
     * @param configValue
     * @param remark
     */
    void saveSysConfig(String configKey, String configValue, String remark);

    /**
     * 修改系统配置
     * @param id
     * @param configKey
     * @param configValue
     * @param remark
     */
    void modifySysConfig(Long id, String configKey, String configValue, String remark);

    /**
     * 删除系统配置
     * @param id
     */
    void deleteSysConfig(Long id);
}
