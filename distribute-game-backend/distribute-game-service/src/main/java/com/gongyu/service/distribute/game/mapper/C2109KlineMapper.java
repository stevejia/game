package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Bu2112Kline;
import com.gongyu.service.distribute.game.model.entity.C2109Kline;
import com.gongyu.service.distribute.game.model.entity.C2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface C2109KlineMapper extends BaseMapper<C2109Kline> {
    int countByExample(C2109KlineExample example);

    int deleteByExample(C2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(C2109Kline record);

    int insertSelective(C2109Kline record);

    List<C2109Kline> selectByExample(C2109KlineExample example);

    C2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") C2109Kline record, @Param("example") C2109KlineExample example);

    int updateByExample(@Param("record") C2109Kline record, @Param("example") C2109KlineExample example);

    int updateByPrimaryKeySelective(C2109Kline record);

    int updateByPrimaryKey(C2109Kline record);
}