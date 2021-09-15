package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Zc201Kline;
import com.gongyu.service.distribute.game.model.entity.Zc201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Zc201KlineMapper {
    int countByExample(Zc201KlineExample example);

    int deleteByExample(Zc201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Zc201Kline record);

    int insertSelective(Zc201Kline record);

    List<Zc201Kline> selectByExample(Zc201KlineExample example);

    Zc201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Zc201Kline record, @Param("example") Zc201KlineExample example);

    int updateByExample(@Param("record") Zc201Kline record, @Param("example") Zc201KlineExample example);

    int updateByPrimaryKeySelective(Zc201Kline record);

    int updateByPrimaryKey(Zc201Kline record);
}