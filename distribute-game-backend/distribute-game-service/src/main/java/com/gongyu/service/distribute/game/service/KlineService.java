package com.gongyu.service.distribute.game.service;

import java.util.List;
import java.util.Map;

import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineExample;
import com.gongyu.service.distribute.game.model.entity.KlineOpenPosition;
import com.gongyu.service.distribute.game.model.entity.KlineTdStructure;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface KlineService extends CrudService<Rb2110Kline>{
	List<KlineDto> queryRbKLine(KlineExample params);
	
	List<KlineDto> queryRbKLine2(KlineExample params, String tableName);
	
	List<KlineDto> refreshKline(KlineExample params);
	
	List<KlineDto> queryKline(KlineExample params, String tableName, int period);
	
	void processKlineTD(String instrumentId, int period);
	void processKlineTD(List<KlineDto> klineList, int startIndex);
	
	//for test
	Map<Integer, Boolean> processReversal(List<KlineDto> kLineList, int index);
	List<KlineTdStructure> processTdStructures(List<KlineDto> klineList, Map<Integer, Boolean> reversalMap);
	void isStructuresPerfect(List<KlineDto> klineList, List<KlineTdStructure> tdStructures);
	List<KlineOpenPosition> isStructuresSatisfyOpenPosition(List<KlineDto> klineList,
			List<KlineTdStructure> tdStructures);
}