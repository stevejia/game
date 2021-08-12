package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futures.model.dto.SysAreaLetterDto;
import com.futures.model.entity.SysArea;
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
