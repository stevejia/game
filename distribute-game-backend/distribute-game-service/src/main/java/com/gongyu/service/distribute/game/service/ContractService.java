package com.gongyu.service.distribute.game.service;

import java.util.List;

import com.gongyu.service.distribute.game.model.entity.Contract;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ContractService extends CrudService<Contract>{
	List<Contract> queryContract();
}
