package com.gongyu.application.distribute.game.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.application.distribute.game.model.RefreshKlineModel;
import com.gongyu.service.distribute.game.common.utils.TupleUtil.ThreeTuple;
import com.gongyu.service.distribute.game.common.utils.TupleUtil.TwoTuple;
import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineExample;
import com.gongyu.service.distribute.game.model.entity.KlineOpenPosition;
import com.gongyu.service.distribute.game.model.entity.KlineTdStructure;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.service.distribute.game.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("kline")
@Api(tags = "k线列表")
public class KLineController {

	@Autowired
	private KlineService klineService;
	
	@ApiOperation(value = "查询k线列表", notes = "查询k线列表", response = KlineDto.class)
	@PostMapping("querykline")
	public BaseResponse queryKLine(@Valid @ModelAttribute KlineDto queryModel) {
		KlineExample param = new KlineExample();
		param.createCriteria().andPeriodEqualTo(queryModel.getPeriod());

		List<KlineDto> kLines = klineService.queryKline(param, queryModel.getInstrumentid(), queryModel.getPeriod());
		return BaseResponse.success(kLines);
	}

	@ApiOperation(value = "刷新K线列表", notes = "刷新K线列表")
	@PostMapping("refreshKline")
	public BaseResponse refreshKline(@Valid @ModelAttribute @RequestBody KlineDto queryModel) {
		KlineExample param = new KlineExample();
		param.createCriteria().andPeriodEqualTo(queryModel.getPeriod());

		ThreeTuple<List<KlineDto>, List<KlineTdStructure>, List<KlineOpenPosition>> result = klineService
				.refreshKline(queryModel);
		RefreshKlineModel response = RefreshKlineModel.builder().klines(result.getFirst())
				.tdStructures(result.getSecond()).openPositions(result.getThird()).build();
		return BaseResponse.success(response);
	}
}