package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Ru2109Kline;
import com.gongyu.service.distribute.game.model.entity.Ru2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ru2109KlineMapper {
    int countByExample(Ru2109KlineExample example);

    int deleteByExample(Ru2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ru2109Kline record);

    int insertSelective(Ru2109Kline record);

    List<Ru2109Kline> selectByExample(Ru2109KlineExample example);

    Ru2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ru2109Kline record, @Param("example") Ru2109KlineExample example);

    int updateByExample(@Param("record") Ru2109Kline record, @Param("example") Ru2109KlineExample example);

    int updateByPrimaryKeySelective(Ru2109Kline record);

    int updateByPrimaryKey(Ru2109Kline record);
}