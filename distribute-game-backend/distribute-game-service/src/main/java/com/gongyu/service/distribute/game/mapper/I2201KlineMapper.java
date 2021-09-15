package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.I2201Kline;
import com.gongyu.service.distribute.game.model.entity.I2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface I2201KlineMapper {
    int countByExample(I2201KlineExample example);

    int deleteByExample(I2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(I2201Kline record);

    int insertSelective(I2201Kline record);

    List<I2201Kline> selectByExample(I2201KlineExample example);

    I2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") I2201Kline record, @Param("example") I2201KlineExample example);

    int updateByExample(@Param("record") I2201Kline record, @Param("example") I2201KlineExample example);

    int updateByPrimaryKeySelective(I2201Kline record);

    int updateByPrimaryKey(I2201Kline record);
}