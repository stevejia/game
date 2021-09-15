package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Ma201Kline;
import com.gongyu.service.distribute.game.model.entity.Ma201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ma201KlineMapper {
    int countByExample(Ma201KlineExample example);

    int deleteByExample(Ma201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ma201Kline record);

    int insertSelective(Ma201Kline record);

    List<Ma201Kline> selectByExample(Ma201KlineExample example);

    Ma201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ma201Kline record, @Param("example") Ma201KlineExample example);

    int updateByExample(@Param("record") Ma201Kline record, @Param("example") Ma201KlineExample example);

    int updateByPrimaryKeySelective(Ma201Kline record);

    int updateByPrimaryKey(Ma201Kline record);
}