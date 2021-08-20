package com.gongyu.service.distribute.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper;
import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.service.distribute.game.utils.BeanCopyUtils;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class KLineServiceImpl extends CrudServiceSupport<Rb2110KlineMapper, Rb2110Kline> implements KlineService  {
	
	@Autowired
	private Rb2110KlineMapper  rb2110KlineMapper;
	
	@Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public List<KlineDto> queryRbKLine(Rb2110KlineExample params) {
    	List<Rb2110Kline> rb2110KLines = rb2110KlineMapper.selectByExample(params);

    	List<KlineDto> dtoList= new ArrayList<KlineDto>();
    	
    	BeanCopyUtils.copyList(rb2110KLines, dtoList, KlineDto.class);
    	//要比较4天前的收盘价 所以索引从第4天开始
    	this.processReversal(dtoList, false, 3);
    	
        return dtoList;
    }
	
	private void processReversal(List<KlineDto> kLineList, boolean isReversal, int startIndex) {
		if(kLineList == null || kLineList.size() < 6 || startIndex > kLineList.size() - 6) {
			return;
		}
		List<KlineDto> sixList = kLineList.subList(startIndex, startIndex+6);
		
		KlineDto k1 = sixList.get(5);
		KlineDto k2 = sixList.get(4);
		KlineDto k5 = sixList.get(1);
		KlineDto k6 = sixList.get(0);
		
		double k1ClosePrice = k1.getCloseprice();
		double k2ClosePrice = k2.getCloseprice();
		double k5ClosePrice = k5.getCloseprice();
		double k6ClosePrice = k6.getCloseprice();
		
		if(k1ClosePrice < k5ClosePrice && k2ClosePrice > k6ClosePrice) {
			
			log.info("熊市反转" + startIndex);
			
		}
		
		if(k1ClosePrice > k5ClosePrice && k2ClosePrice < k6ClosePrice) {
			
			log.info("牛市反转" + startIndex);
			
		}
		
		this.processReversal(kLineList, isReversal, ++startIndex);
		
	}
}