package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigDeletePageDTO;
import com.gongyu.service.distribute.game.model.dto.PigDeletePageReqDto;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDelete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExclusivePigDeleteMapper extends BaseMapper<UserExclusivePigDelete> {

    List<PigDeletePageDTO> findPage(IPage<PigDeletePageDTO> page, @Param("param") PigDeletePageReqDto param);
}