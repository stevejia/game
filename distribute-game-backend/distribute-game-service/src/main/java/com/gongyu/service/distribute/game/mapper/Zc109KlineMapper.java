package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Zc109Kline;
import com.gongyu.service.distribute.game.model.entity.Zc109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Zc109KlineMapper {
    int countByExample(Zc109KlineExample example);

    int deleteByExample(Zc109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Zc109Kline record);

    int insertSelective(Zc109Kline record);

    List<Zc109Kline> selectByExample(Zc109KlineExample example);

    Zc109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Zc109Kline record, @Param("example") Zc109KlineExample example);

    int updateByExample(@Param("record") Zc109Kline record, @Param("example") Zc109KlineExample example);

    int updateByPrimaryKeySelective(Zc109Kline record);

    int updateByPrimaryKey(Zc109Kline record);
}