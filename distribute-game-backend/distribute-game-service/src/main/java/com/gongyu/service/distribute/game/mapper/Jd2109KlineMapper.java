package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.J2201Kline;
import com.gongyu.service.distribute.game.model.entity.Jd2109Kline;
import com.gongyu.service.distribute.game.model.entity.Jd2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Jd2109KlineMapper extends BaseMapper<Jd2109Kline> {
    int countByExample(Jd2109KlineExample example);

    int deleteByExample(Jd2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Jd2109Kline record);

    int insertSelective(Jd2109Kline record);

    List<Jd2109Kline> selectByExample(Jd2109KlineExample example);

    Jd2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Jd2109Kline record, @Param("example") Jd2109KlineExample example);

    int updateByExample(@Param("record") Jd2109Kline record, @Param("example") Jd2109KlineExample example);

    int updateByPrimaryKeySelective(Jd2109Kline record);

    int updateByPrimaryKey(Jd2109Kline record);
}