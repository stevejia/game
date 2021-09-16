package com.gongyu.application.distribute.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gongyu.service.distribute.game.model.entity.Contract;
import com.gongyu.service.distribute.game.service.ContractService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("contract")
@Api(tags = "合约列表")
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@ApiOperation(value = "查询合约", notes = "查询合约", response = Contract.class)
	@PostMapping("query")
	public BaseResponse queryContract() {
		List<Contract> contracts = contractService.queryContract();
		return BaseResponse.success(contracts);
	}
}
