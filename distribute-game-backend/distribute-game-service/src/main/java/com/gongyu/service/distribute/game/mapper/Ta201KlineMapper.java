package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Ta109Kline;
import com.gongyu.service.distribute.game.model.entity.Ta201Kline;
import com.gongyu.service.distribute.game.model.entity.Ta201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ta201KlineMapper extends BaseMapper<Ta201Kline> {
    int countByExample(Ta201KlineExample example);

    int deleteByExample(Ta201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ta201Kline record);

    int insertSelective(Ta201Kline record);

    List<Ta201Kline> selectByExample(Ta201KlineExample example);

    Ta201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ta201Kline record, @Param("example") Ta201KlineExample example);

    int updateByExample(@Param("record") Ta201Kline record, @Param("example") Ta201KlineExample example);

    int updateByPrimaryKeySelective(Ta201Kline record);

    int updateByPrimaryKey(Ta201Kline record);
}