package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.entity.Sa201Kline;
import com.gongyu.service.distribute.game.model.entity.Ta109Kline;
import com.gongyu.service.distribute.game.model.entity.Ta109KlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Ta109KlineMapper extends BaseMapper<Ta109Kline> {
	int countByExample(Ta109KlineExample example);

	int deleteByExample(Ta109KlineExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Ta109Kline record);

	int insertSelective(Ta109Kline record);

	List<Ta109Kline> selectByExample(Ta109KlineExample example);

	Ta109Kline selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Ta109Kline record, @Param("example") Ta109KlineExample example);

	int updateByExample(@Param("record") Ta109Kline record, @Param("example") Ta109KlineExample example);

	int updateByPrimaryKeySelective(Ta109Kline record);

	int updateByPrimaryKey(Ta109Kline record);
}