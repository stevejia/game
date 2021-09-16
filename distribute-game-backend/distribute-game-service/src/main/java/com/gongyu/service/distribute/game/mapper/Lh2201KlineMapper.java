package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Jm2201Kline;
import com.gongyu.service.distribute.game.model.entity.Lh2201Kline;
import com.gongyu.service.distribute.game.model.entity.Lh2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Lh2201KlineMapper extends BaseMapper<Lh2201Kline> {
    int countByExample(Lh2201KlineExample example);

    int deleteByExample(Lh2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Lh2201Kline record);

    int insertSelective(Lh2201Kline record);

    List<Lh2201Kline> selectByExample(Lh2201KlineExample example);

    Lh2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Lh2201Kline record, @Param("example") Lh2201KlineExample example);

    int updateByExample(@Param("record") Lh2201Kline record, @Param("example") Lh2201KlineExample example);

    int updateByPrimaryKeySelective(Lh2201Kline record);

    int updateByPrimaryKey(Lh2201Kline record);
}