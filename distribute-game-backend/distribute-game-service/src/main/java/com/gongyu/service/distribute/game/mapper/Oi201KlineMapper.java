package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Ma201Kline;
import com.gongyu.service.distribute.game.model.entity.Oi201Kline;
import com.gongyu.service.distribute.game.model.entity.Oi201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Oi201KlineMapper extends BaseMapper<Oi201Kline> {
    int countByExample(Oi201KlineExample example);

    int deleteByExample(Oi201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Oi201Kline record);

    int insertSelective(Oi201Kline record);

    List<Oi201Kline> selectByExample(Oi201KlineExample example);

    Oi201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Oi201Kline record, @Param("example") Oi201KlineExample example);

    int updateByExample(@Param("record") Oi201Kline record, @Param("example") Oi201KlineExample example);

    int updateByPrimaryKeySelective(Oi201Kline record);

    int updateByPrimaryKey(Oi201Kline record);
}