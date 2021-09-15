package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.C2201Kline;
import com.gongyu.service.distribute.game.model.entity.C2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface C2201KlineMapper {
    int countByExample(C2201KlineExample example);

    int deleteByExample(C2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(C2201Kline record);

    int insertSelective(C2201Kline record);

    List<C2201Kline> selectByExample(C2201KlineExample example);

    C2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") C2201Kline record, @Param("example") C2201KlineExample example);

    int updateByExample(@Param("record") C2201Kline record, @Param("example") C2201KlineExample example);

    int updateByPrimaryKeySelective(C2201Kline record);

    int updateByPrimaryKey(C2201Kline record);
}