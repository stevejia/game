package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Bu2112Kline;
import com.gongyu.service.distribute.game.model.entity.Bu2112KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bu2112KlineMapper extends BaseMapper<Bu2112Kline> {
    int countByExample(Bu2112KlineExample example);

    int deleteByExample(Bu2112KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bu2112Kline record);

    int insertSelective(Bu2112Kline record);

    List<Bu2112Kline> selectByExample(Bu2112KlineExample example);

    Bu2112Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bu2112Kline record, @Param("example") Bu2112KlineExample example);

    int updateByExample(@Param("record") Bu2112Kline record, @Param("example") Bu2112KlineExample example);

    int updateByPrimaryKeySelective(Bu2112Kline record);

    int updateByPrimaryKey(Bu2112Kline record);
}