package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Y2201Kline;
import com.gongyu.service.distribute.game.model.entity.Y2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Y2201KlineMapper {
    int countByExample(Y2201KlineExample example);

    int deleteByExample(Y2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Y2201Kline record);

    int insertSelective(Y2201Kline record);

    List<Y2201Kline> selectByExample(Y2201KlineExample example);

    Y2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Y2201Kline record, @Param("example") Y2201KlineExample example);

    int updateByExample(@Param("record") Y2201Kline record, @Param("example") Y2201KlineExample example);

    int updateByPrimaryKeySelective(Y2201Kline record);

    int updateByPrimaryKey(Y2201Kline record);
}