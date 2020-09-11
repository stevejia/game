package com.gongyu.service.distribute.game.service;

import com.gongyu.service.distribute.game.model.dto.SysAreaLetterDto;
import com.gongyu.service.distribute.game.model.entity.SysArea;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;

public interface SysAreaService extends CrudService<SysArea> {
    /**
     * 查询所有城市（拼音首字母排序）
     * @return
     */
    List<SysAreaLetterDto> queryCityByLetterOrder();
}
