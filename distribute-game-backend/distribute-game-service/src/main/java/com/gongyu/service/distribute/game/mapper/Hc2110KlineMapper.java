package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Fu2201Kline;
import com.gongyu.service.distribute.game.model.entity.Hc2110Kline;
import com.gongyu.service.distribute.game.model.entity.Hc2110KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Hc2110KlineMapper extends BaseMapper<Hc2110Kline> {
    int countByExample(Hc2110KlineExample example);

    int deleteByExample(Hc2110KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hc2110Kline record);

    int insertSelective(Hc2110Kline record);

    List<Hc2110Kline> selectByExample(Hc2110KlineExample example);

    Hc2110Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hc2110Kline record, @Param("example") Hc2110KlineExample example);

    int updateByExample(@Param("record") Hc2110Kline record, @Param("example") Hc2110KlineExample example);

    int updateByPrimaryKeySelective(Hc2110Kline record);

    int updateByPrimaryKey(Hc2110Kline record);
}