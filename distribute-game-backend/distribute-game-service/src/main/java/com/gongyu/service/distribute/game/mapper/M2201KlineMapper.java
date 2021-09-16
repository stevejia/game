package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Lh2201Kline;
import com.gongyu.service.distribute.game.model.entity.M2201Kline;
import com.gongyu.service.distribute.game.model.entity.M2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface M2201KlineMapper extends BaseMapper<M2201Kline> {
    int countByExample(M2201KlineExample example);

    int deleteByExample(M2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(M2201Kline record);

    int insertSelective(M2201Kline record);

    List<M2201Kline> selectByExample(M2201KlineExample example);

    M2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") M2201Kline record, @Param("example") M2201KlineExample example);

    int updateByExample(@Param("record") M2201Kline record, @Param("example") M2201KlineExample example);

    int updateByPrimaryKeySelective(M2201Kline record);

    int updateByPrimaryKey(M2201Kline record);
}