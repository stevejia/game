package com.gongyu.service.distribute.game.service;


import com.gongyu.service.distribute.game.model.dto.SysAreaLetterDto;
import com.gongyu.service.distribute.game.model.dto.SysAreaStructuredDto;
import com.gongyu.service.distribute.game.model.dto.SysAreaUnstructuredDto;

import java.util.List;

public interface SysAreaFpi {
    /**
     * 查询所有城市（拼音首字母排序）
     * @return
     */
    List<SysAreaLetterDto> queryCityByLetterOrder();

    /**
     * 获取所有区域（平铺、父子非结构化）
     * @return
     */
    List<SysAreaUnstructuredDto> queryAreaUnStructured();

    /**
     * 根据父ID查询子区域
     * @param parentId
     * @return
     */
    List<SysAreaUnstructuredDto> queryAreaByParentId(Long parentId);

    /**
     * 获取所有区域（父子结构化）
     * @return
     */
    List<SysAreaStructuredDto> queryAreaStructured();
}
