package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Cf201Kline;
import com.gongyu.service.distribute.game.model.entity.Fg201Kline;
import com.gongyu.service.distribute.game.model.entity.Fg201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Fg201KlineMapper  extends BaseMapper<Fg201Kline> {
    int countByExample(Fg201KlineExample example);

    int deleteByExample(Fg201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Fg201Kline record);

    int insertSelective(Fg201Kline record);

    List<Fg201Kline> selectByExample(Fg201KlineExample example);

    Fg201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Fg201Kline record, @Param("example") Fg201KlineExample example);

    int updateByExample(@Param("record") Fg201Kline record, @Param("example") Fg201KlineExample example);

    int updateByPrimaryKeySelective(Fg201Kline record);

    int updateByPrimaryKey(Fg201Kline record);
}