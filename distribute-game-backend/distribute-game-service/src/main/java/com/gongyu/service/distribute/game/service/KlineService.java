package com.gongyu.service.distribute.game.service;

import java.util.List;

import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface KlineService extends CrudService<Rb2110Kline>{
	List<KlineDto> queryRbKLine(Rb2110KlineExample params);
}