package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.Ap110Kline;
import com.gongyu.service.distribute.game.model.entity.Ap110KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ap110KlineMapper {
    int countByExample(Ap110KlineExample example);

    int deleteByExample(Ap110KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ap110Kline record);

    int insertSelective(Ap110Kline record);

    List<Ap110Kline> selectByExample(Ap110KlineExample example);

    Ap110Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ap110Kline record, @Param("example") Ap110KlineExample example);

    int updateByExample(@Param("record") Ap110Kline record, @Param("example") Ap110KlineExample example);

    int updateByPrimaryKeySelective(Ap110Kline record);

    int updateByPrimaryKey(Ap110Kline record);
}