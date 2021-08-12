package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigAwardLogPageDto;
import com.futures.model.entity.PigAwardLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigAwardLogMapper extends BaseMapper<PigAwardLog> {


    List<PigAwardLogPageDto> findPage(IPage<PigAwardLogPageDto> page, @Param("param") PigAwardLogPageDto dto);
}