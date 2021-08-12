package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigDeletePageDTO;
import com.futures.model.dto.PigDeletePageReqDto;
import com.futures.model.entity.UserExclusivePigDelete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExclusivePigDeleteMapper extends BaseMapper<UserExclusivePigDelete> {

    List<PigDeletePageDTO> findPage(IPage<PigDeletePageDTO> page, @Param("param") PigDeletePageReqDto param);
}