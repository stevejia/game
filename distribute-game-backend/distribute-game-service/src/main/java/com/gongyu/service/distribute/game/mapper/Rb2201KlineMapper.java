package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Rb2201Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Rb2201KlineMapper {
    int countByExample(Rb2201KlineExample example);

    int deleteByExample(Rb2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Rb2201Kline record);

    int insertSelective(Rb2201Kline record);

    List<Rb2201Kline> selectByExample(Rb2201KlineExample example);

    Rb2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Rb2201Kline record, @Param("example") Rb2201KlineExample example);

    int updateByExample(@Param("record") Rb2201Kline record, @Param("example") Rb2201KlineExample example);

    int updateByPrimaryKeySelective(Rb2201Kline record);

    int updateByPrimaryKey(Rb2201Kline record);
}