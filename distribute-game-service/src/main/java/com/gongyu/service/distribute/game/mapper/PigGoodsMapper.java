package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface PigGoodsMapper extends BaseMapper<PigGoods> {

    @Select("SELECT * from zp_pig_goods ORDER BY large_price desc LIMIT 1")
    PigGoods findMaxPrice();
}
