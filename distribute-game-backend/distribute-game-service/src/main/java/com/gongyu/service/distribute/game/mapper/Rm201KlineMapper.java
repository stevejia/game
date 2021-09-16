package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Rb2201Kline;
import com.gongyu.service.distribute.game.model.entity.Rm201Kline;
import com.gongyu.service.distribute.game.model.entity.Rm201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Rm201KlineMapper extends BaseMapper<Rm201Kline> {
    int countByExample(Rm201KlineExample example);

    int deleteByExample(Rm201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Rm201Kline record);

    int insertSelective(Rm201Kline record);

    List<Rm201Kline> selectByExample(Rm201KlineExample example);

    Rm201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Rm201Kline record, @Param("example") Rm201KlineExample example);

    int updateByExample(@Param("record") Rm201Kline record, @Param("example") Rm201KlineExample example);

    int updateByPrimaryKeySelective(Rm201Kline record);

    int updateByPrimaryKey(Rm201Kline record);
}