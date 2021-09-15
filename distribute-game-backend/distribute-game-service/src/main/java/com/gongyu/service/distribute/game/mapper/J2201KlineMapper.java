package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.J2201Kline;
import com.gongyu.service.distribute.game.model.entity.J2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface J2201KlineMapper {
    int countByExample(J2201KlineExample example);

    int deleteByExample(J2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(J2201Kline record);

    int insertSelective(J2201Kline record);

    List<J2201Kline> selectByExample(J2201KlineExample example);

    J2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") J2201Kline record, @Param("example") J2201KlineExample example);

    int updateByExample(@Param("record") J2201Kline record, @Param("example") J2201KlineExample example);

    int updateByPrimaryKeySelective(J2201Kline record);

    int updateByPrimaryKey(J2201Kline record);
}