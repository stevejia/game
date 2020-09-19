package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigReservationModifyDto;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigReservationMapper extends BaseMapper<PigReservation> {


    List<PigReservationModifyDto> findPage(IPage<PigReservationModifyDto> page, @Param("param") PigReservationModifyDto param);
    
    List<PigReservationModifyDto> findAll(@Param("param") PigReservationModifyDto param);

    @Select("select * from zp_pig_reservation where user_id = #{userId} and pig_id = #{pigId} and reservation_scene = #{awardId} order by id desc")
    PigReservation findByUserAndPig(@Param("userId") Long userId,@Param("pigId") Long pigId,@Param("awardId") Long awardId);

    @Select("select * from zp_pig_reservation where user_id = #{userId} order by id desc")
    List<PigReservation> findPageByUser(IPage page,@Param("userId") Long userId);

    @Select("select a.* from zp_pig_reservation a inner join zp_pig_award_log b on a.reservation_scene = b.id" +
            " where a.user_id = #{userId} and a.pig_id = #{pigId} and b.open_result = 0")
    List<PigReservation> findReservated(@Param("userId") Long userId,@Param("pigId") Long pigId);
}