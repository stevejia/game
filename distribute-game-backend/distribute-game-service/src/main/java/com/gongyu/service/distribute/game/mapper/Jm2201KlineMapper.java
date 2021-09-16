package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Jd2201Kline;
import com.gongyu.service.distribute.game.model.entity.Jm2201Kline;
import com.gongyu.service.distribute.game.model.entity.Jm2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Jm2201KlineMapper extends BaseMapper<Jm2201Kline> {
    int countByExample(Jm2201KlineExample example);

    int deleteByExample(Jm2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Jm2201Kline record);

    int insertSelective(Jm2201Kline record);

    List<Jm2201Kline> selectByExample(Jm2201KlineExample example);

    Jm2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Jm2201Kline record, @Param("example") Jm2201KlineExample example);

    int updateByExample(@Param("record") Jm2201Kline record, @Param("example") Jm2201KlineExample example);

    int updateByPrimaryKeySelective(Jm2201Kline record);

    int updateByPrimaryKey(Jm2201Kline record);
}