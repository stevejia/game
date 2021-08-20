package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Rb2110KlineMapper extends BaseMapper<Rb2110Kline> {
    int countByExample(Rb2110KlineExample example);

    int deleteByExample(Rb2110KlineExample example);

    int insert(Rb2110KlineWithBLOBs record);

    int insertSelective(Rb2110KlineWithBLOBs record);

    List<Rb2110KlineWithBLOBs> selectByExampleWithBLOBs(Rb2110KlineExample example);

    List<Rb2110Kline> selectByExample(Rb2110KlineExample example);

    int updateByExampleSelective(@Param("record") Rb2110KlineWithBLOBs record, @Param("example") Rb2110KlineExample example);

    int updateByExampleWithBLOBs(@Param("record") Rb2110KlineWithBLOBs record, @Param("example") Rb2110KlineExample example);

    int updateByExample(@Param("record") Rb2110Kline record, @Param("example") Rb2110KlineExample example);
}