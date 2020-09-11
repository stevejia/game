package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.dto.SysAreaLetterDto;
import com.gongyu.service.distribute.game.model.entity.SysArea;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAreaMapper extends BaseMapper<SysArea> {
    /**
     * 查询所有城市（拼音首字母排序）
     * @return
     */
    List<SysAreaLetterDto> queryCityByLetterOrder();
}
