package com.gongyu.application.distribute.game.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.service.TestKlineService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("testkline")
@Api(tags = "k线列表")
public class TestKLineController {

	@Autowired
	private TestKlineService testKlineService;

	@ApiOperation(value = "测试", notes = "刷新K线列表")
	@PostMapping("testTDStructure")
	public BaseResponse testTDStructure() {
		testKlineService.testProcessKlineTD();
		return BaseResponse.success();
	}
}