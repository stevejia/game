package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Fg201Kline;
import com.gongyu.service.distribute.game.model.entity.Fu2109Kline;
import com.gongyu.service.distribute.game.model.entity.Fu2109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Fu2109KlineMapper  extends BaseMapper<Fu2109Kline> {
    int countByExample(Fu2109KlineExample example);

    int deleteByExample(Fu2109KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Fu2109Kline record);

    int insertSelective(Fu2109Kline record);

    List<Fu2109Kline> selectByExample(Fu2109KlineExample example);

    Fu2109Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Fu2109Kline record, @Param("example") Fu2109KlineExample example);

    int updateByExample(@Param("record") Fu2109Kline record, @Param("example") Fu2109KlineExample example);

    int updateByPrimaryKeySelective(Fu2109Kline record);

    int updateByPrimaryKey(Fu2109Kline record);
}