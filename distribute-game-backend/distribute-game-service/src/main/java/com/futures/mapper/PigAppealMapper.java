package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.entity.PigAppeal;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigAppealMapper extends BaseMapper<PigAppeal> {

    @Select("select * from zp_pig_appeal where user_id = #{userId}")
    List<PigAppeal> findPage(IPage page, @Param("userId") Long userId);
}