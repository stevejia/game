package com.gongyu.service.distribute.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gongyu.service.distribute.game.mapper.ContractMapper;
import com.gongyu.service.distribute.game.model.entity.Contract;
import com.gongyu.service.distribute.game.service.ContractService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@DS("config")
public class ContractServiceImpl extends CrudServiceSupport<ContractMapper, Contract> implements ContractService {
	@Autowired
	private ContractMapper mapper;
	
	@Override
	public List<Contract> queryContract() {
		List<Contract> contracts = mapper.selectByExample(null);
		return contracts;
	}
	

}
