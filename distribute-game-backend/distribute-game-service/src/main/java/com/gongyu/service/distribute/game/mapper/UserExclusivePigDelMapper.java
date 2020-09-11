package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigDelPageReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDelDTO;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserExclusivePigDelMapper  extends BaseMapper<UserExclusivePigDel> {


    List<UserExclusivePigDelDTO> findPage(IPage<UserExclusivePigDelDTO> page, @Param("param") PigDelPageReqDto param);

    @Select("select * from zp_user_exclusive_pig_del where user_id = #{userId}")
    List<UserExclusivePigDel> findPageByUser(IPage page, @Param("userId") Long userId);

    void add(UserExclusivePigDel pigDel);
}