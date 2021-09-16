package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Fu2109Kline;
import com.gongyu.service.distribute.game.model.entity.Fu2201Kline;
import com.gongyu.service.distribute.game.model.entity.Fu2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Fu2201KlineMapper extends BaseMapper<Fu2201Kline> {
    int countByExample(Fu2201KlineExample example);

    int deleteByExample(Fu2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Fu2201Kline record);

    int insertSelective(Fu2201Kline record);

    List<Fu2201Kline> selectByExample(Fu2201KlineExample example);

    Fu2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Fu2201Kline record, @Param("example") Fu2201KlineExample example);

    int updateByExample(@Param("record") Fu2201Kline record, @Param("example") Fu2201KlineExample example);

    int updateByPrimaryKeySelective(Fu2201Kline record);

    int updateByPrimaryKey(Fu2201Kline record);
}