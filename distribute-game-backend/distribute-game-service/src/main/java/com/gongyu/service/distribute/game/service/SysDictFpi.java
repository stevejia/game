package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.SysDict;
import com.gongyu.service.distribute.game.model.entity.SysDictItem;

import java.util.List;

public interface SysDictFpi {
    /**
     * 字典查询
     * @param page
     * @param dictCode
     * @return
     */
    IPage<SysDict> querySysDict(IPage<SysDict> page, String dictCode);

    /**
     * 新增字典
     * @param sysDictSaveDto
     * @return
     */
    SysDict saveSysDict(SysDictSaveDto sysDictSaveDto);

    /**
     * 修改字典
     * @param sysDictModifyDto
     * @return
     */
    SysDict modifySysDict(SysDictModifyDto sysDictModifyDto);

    /**
     * 删除字典
     * @param id
     */
    boolean deleteSysDict( Long id);

    /**
     * 字典项查询
     * @param page
     * @param dictCode
     * @return
     */
    IPage<SysDictItem> querySysDictItem(IPage<SysDictItem> page, String dictCode);

    /**
     * 新增字典项
     * @param sysDictItemSaveDto
     */
    void saveSysDictItem(SysDictItemSaveDto sysDictItemSaveDto);

    /**
     * 修改字典项
     * @param sysDictItemModifyDto
     */
    void modifySysDictItem(SysDictItemModifyDto sysDictItemModifyDto);

    /**
     * 删除字典项
     * @param id
     * @return
     */
    boolean deleteSysDictItem(Long id);

    /**
     * 根据字典码查询所有字典项
     * @param dictCode
     * @return
     */
    List<SysDictItem> getSysDictItemList(String dictCode);

    /**
     * 根据字典码前缀查询所有字典项
     * @param dictCodePrefix
     * @return
     */
    List<SysDictDto> getSysDictItemListByPrefix(String dictCodePrefix);
}
