package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Ap110Kline;
import com.gongyu.service.distribute.game.model.entity.Ap201Kline;
import com.gongyu.service.distribute.game.model.entity.Ap201KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ap201KlineMapper extends BaseMapper<Ap201Kline> {
    int countByExample(Ap201KlineExample example);

    int deleteByExample(Ap201KlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ap201Kline record);

    int insertSelective(Ap201Kline record);

    List<Ap201Kline> selectByExample(Ap201KlineExample example);

    Ap201Kline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ap201Kline record, @Param("example") Ap201KlineExample example);

    int updateByExample(@Param("record") Ap201Kline record, @Param("example") Ap201KlineExample example);

    int updateByPrimaryKeySelective(Ap201Kline record);

    int updateByPrimaryKey(Ap201Kline record);
}