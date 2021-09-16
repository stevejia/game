package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.C2201Kline;
import com.gongyu.service.distribute.game.model.entity.Cf201Kline;
import com.gongyu.service.distribute.game.model.entity.Cf201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Cf201KlineMapper  extends BaseMapper<Cf201Kline> {
    int countByExample(Cf201KlineExample example);

    int deleteByExample(Cf201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cf201Kline record);

    int insertSelective(Cf201Kline record);

    List<Cf201Kline> selectByExample(Cf201KlineExample example);

    Cf201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cf201Kline record, @Param("example") Cf201KlineExample example);

    int updateByExample(@Param("record") Cf201Kline record, @Param("example") Cf201KlineExample example);

    int updateByPrimaryKeySelective(Cf201Kline record);

    int updateByPrimaryKey(Cf201Kline record);
}