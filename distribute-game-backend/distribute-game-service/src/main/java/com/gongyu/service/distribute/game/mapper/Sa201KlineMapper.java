package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Ru2201Kline;
import com.gongyu.service.distribute.game.model.entity.Sa201Kline;
import com.gongyu.service.distribute.game.model.entity.Sa201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Sa201KlineMapper extends BaseMapper<Sa201Kline> {
    int countByExample(Sa201KlineExample example);

    int deleteByExample(Sa201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sa201Kline record);

    int insertSelective(Sa201Kline record);

    List<Sa201Kline> selectByExample(Sa201KlineExample example);

    Sa201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sa201Kline record, @Param("example") Sa201KlineExample example);

    int updateByExample(@Param("record") Sa201Kline record, @Param("example") Sa201KlineExample example);

    int updateByPrimaryKeySelective(Sa201Kline record);

    int updateByPrimaryKey(Sa201Kline record);
}