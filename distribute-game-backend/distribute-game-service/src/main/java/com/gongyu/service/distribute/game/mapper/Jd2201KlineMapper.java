package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Jd2109Kline;
import com.gongyu.service.distribute.game.model.entity.Jd2201Kline;
import com.gongyu.service.distribute.game.model.entity.Jd2201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Jd2201KlineMapper extends BaseMapper<Jd2201Kline> {
    int countByExample(Jd2201KlineExample example);

    int deleteByExample(Jd2201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Jd2201Kline record);

    int insertSelective(Jd2201Kline record);

    List<Jd2201Kline> selectByExample(Jd2201KlineExample example);

    Jd2201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Jd2201Kline record, @Param("example") Jd2201KlineExample example);

    int updateByExample(@Param("record") Jd2201Kline record, @Param("example") Jd2201KlineExample example);

    int updateByPrimaryKeySelective(Jd2201Kline record);

    int updateByPrimaryKey(Jd2201Kline record);
}