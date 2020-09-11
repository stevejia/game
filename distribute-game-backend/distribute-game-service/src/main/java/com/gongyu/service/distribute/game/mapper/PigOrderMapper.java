package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.AdoptReqDto;
import com.gongyu.service.distribute.game.model.dto.PigOrderModifyDto;
import com.gongyu.service.distribute.game.model.dto.TransfReqDto;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigOrderMapper extends BaseMapper<PigOrder> {

    @Select("select * from zp_pig_order where pig_order_sn = #{orderNo}")
    PigOrder getByOrderNo(@Param("orderNo") String orderNo);

    @Select("select * from zp_pig_order where purchase_user_id = #{userId} and pig_level = #{goodsId} and pay_status = 1 order by order_id desc limit 1")
    PigOrder getOrderByUser(@Param("userId") Long userId,@Param("goodsId") Long goodsId);

    List<PigOrderModifyDto> findPage(IPage<PigOrderModifyDto> page, @Param("param") PigOrderModifyDto param);

    List<PigOrder> findPageByUser(IPage page,@Param("userId") Long userId,@Param("param") AdoptReqDto param);

    List<PigOrder> findPageBySell(IPage page,@Param("userId") Long userId,@Param("param") TransfReqDto param);

    int insertForeach(List<PigOrder> list);

    @Select("select * from zp_pig_order where purchase_user_id = #{userId} limit 1")
    PigOrder findByBuyUser(@Param("userId") Long userId);

    @Select("select * from zp_pig_order where sell_user_id = #{userId} limit 1")
    PigOrder findBySellUser(@Param("userId") Long userId);
}