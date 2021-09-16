package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Hc2110Kline;
import com.gongyu.service.distribute.game.model.entity.Hc2201Kline;
import com.gongyu.service.distribute.game.model.entity.Hc2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Hc2201KlineMapper extends BaseMapper<Hc2201Kline> {
    int countByExample(Hc2201KlineExample example);

    int deleteByExample(Hc2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hc2201Kline record);

    int insertSelective(Hc2201Kline record);

    List<Hc2201Kline> selectByExample(Hc2201KlineExample example);

    Hc2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hc2201Kline record, @Param("example") Hc2201KlineExample example);

    int updateByExample(@Param("record") Hc2201Kline record, @Param("example") Hc2201KlineExample example);

    int updateByPrimaryKeySelective(Hc2201Kline record);

    int updateByPrimaryKey(Hc2201Kline record);
}