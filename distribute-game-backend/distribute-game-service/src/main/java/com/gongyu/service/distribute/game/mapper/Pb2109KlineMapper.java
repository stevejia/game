package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Pb2109Kline;
import com.gongyu.service.distribute.game.model.entity.Pb2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Pb2109KlineMapper {
    int countByExample(Pb2109KlineExample example);

    int deleteByExample(Pb2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pb2109Kline record);

    int insertSelective(Pb2109Kline record);

    List<Pb2109Kline> selectByExample(Pb2109KlineExample example);

    Pb2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pb2109Kline record, @Param("example") Pb2109KlineExample example);

    int updateByExample(@Param("record") Pb2109Kline record, @Param("example") Pb2109KlineExample example);

    int updateByPrimaryKeySelective(Pb2109Kline record);

    int updateByPrimaryKey(Pb2109Kline record);
}