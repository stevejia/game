package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Ru2109Kline;
import com.gongyu.service.distribute.game.model.entity.Ru2201Kline;
import com.gongyu.service.distribute.game.model.entity.Ru2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ru2201KlineMapper extends BaseMapper<Ru2201Kline> {
    int countByExample(Ru2201KlineExample example);

    int deleteByExample(Ru2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ru2201Kline record);

    int insertSelective(Ru2201Kline record);

    List<Ru2201Kline> selectByExample(Ru2201KlineExample example);

    Ru2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ru2201Kline record, @Param("example") Ru2201KlineExample example);

    int updateByExample(@Param("record") Ru2201Kline record, @Param("example") Ru2201KlineExample example);

    int updateByPrimaryKeySelective(Ru2201Kline record);

    int updateByPrimaryKey(Ru2201Kline record);
}