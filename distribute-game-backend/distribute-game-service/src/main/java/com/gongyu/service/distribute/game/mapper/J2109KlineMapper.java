package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.I2201Kline;
import com.gongyu.service.distribute.game.model.entity.J2109Kline;
import com.gongyu.service.distribute.game.model.entity.J2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface J2109KlineMapper extends BaseMapper<J2109Kline> {
    int countByExample(J2109KlineExample example);

    int deleteByExample(J2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(J2109Kline record);

    int insertSelective(J2109Kline record);

    List<J2109Kline> selectByExample(J2109KlineExample example);

    J2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") J2109Kline record, @Param("example") J2109KlineExample example);

    int updateByExample(@Param("record") J2109Kline record, @Param("example") J2109KlineExample example);

    int updateByPrimaryKeySelective(J2109Kline record);

    int updateByPrimaryKey(J2109Kline record);
}