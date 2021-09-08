package com.gongyu.service.distribute.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper;
import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineTdStructure;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.service.distribute.game.service.TestKlineService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;

import lombok.extern.slf4j.Slf4j;

@Service
public class TestKlineServiceImpl extends CrudServiceSupport<Rb2110KlineMapper, Rb2110Kline>
		implements TestKlineService {
	@Autowired
	private KlineService klineService;

	@Override
	public void testProcessKlineTD() {
		this.testProcessReversal();
	}

	private void testProcessReversal() {
		// 测试熊市反转
		List<Double> mockClosePriceList = Arrays.asList(1.0, 7.0, 3.0, 4.0, 5.0, 6.0);
		List<KlineDto> mockKlineList = mockClosePriceList.stream().map(price -> {
			KlineDto kline = new KlineDto();
			kline.setCloseprice(price);
			return kline;
		}).collect(Collectors.toList());
//		klineService.processReversal(mockKlineList, 0);
//
//		// 测试牛市反转
//		mockClosePriceList = Arrays.asList(6.0, 5.0, 6.0, 4.0, 5.0, 6.0);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processReversal(mockKlineList, 0);

//		// 测试TD买入结构
//		// 连续9根K线的收盘价都低于它们相应的4天前的收盘价
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.4, 3.9, 4.8, 5.7, 5.3, 3.8, 4.7, 5.6);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		Map<Integer, Boolean> reversalMap = new HashMap<Integer, Boolean>();
//		reversalMap.put(5, true);
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.6, 3.9, 4.8, 5.7, 5.3, 3.8, 4.7, 5.6);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.6, 3.9, 4.8, 5.7, 5.3, 3.8);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.4, 3.9, 4.8, 5.7, 5.3, 3.8);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		// 测试TD卖出结构
//		//连续9根K线的收盘价都高于它们相应的4天前的收盘价
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.6, 4.9, 5.8, 7.2, 5.8, 5.2, 5.9, 7.4); 
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		reversalMap = new HashMap<Integer, Boolean>();
//		reversalMap.put(5, false);
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 5.8, 7.2, 5.8, 5.2, 5.9, 7.4);
//		mockKlineList = mockClosePriceList.stream().map(price -> { 
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 5.8, 7.2, 5.8);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);
//
//		mockClosePriceList = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.6, 4.9, 5.8, 7.2, 5.8);
//		mockKlineList = mockClosePriceList.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setCloseprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		klineService.processTdStructures(mockKlineList, reversalMap);

		// 买入结构完善测试
		List<Double> mockLowestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.4, 4.7, 4.8, 5.7, 5.3, 4.6, 4.6,
				5.6, 5.9, 6.1, 6.8, 7.7, 9.8);
		mockKlineList = mockLowestPrice.stream().map(price -> {
			KlineDto kline = new KlineDto();
			kline.setLowestprice(price);
			return kline;
		}).collect(Collectors.toList());
		List<KlineTdStructure> tdStructures = Arrays
				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(true).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//		
//		mockLowestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.4, 4.7, 4.8, 5.7, 5.3, 4.6, 4.6,
//				5.6, 5.9, 6.1, 6.8, 4.5, 9.8); 
//		mockKlineList = mockLowestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setLowestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(true).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//		
//		mockLowestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 6.0, 5.4, 4.7, 4.8, 5.7, 5.3, 4.6, 4.6,
//				4.5);
//		mockKlineList = mockLowestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setLowestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(true).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);

//		// 卖出结构完善标记测试
//
//		List<Double> mockHighestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 4.7, 4.6, 4.3, 4.2, 4.1,
//				4.0);
//		mockKlineList = mockHighestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setHighestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(false).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//
//		mockHighestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 4.7, 4.6, 4.3, 4.2, 4.1, 5.6);
//		mockKlineList = mockHighestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setHighestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(false).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//
//		mockHighestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 4.7, 4.6, 4.3, 4.2, 4.1, 4.0, 4.0, 4.0,
//				4.0, 4.2);
//		mockKlineList = mockHighestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setHighestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(false).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//
//		mockHighestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 4.7, 4.6, 4.3, 4.2, 4.1, 4.0, 4.0, 4.0,
//				4.0, 4.4);
//		mockKlineList = mockHighestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setHighestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(false).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
//
//		mockHighestPrice = Arrays.asList(1.0, 7.0, 5.5, 4.0, 5.0, 7.1, 5.4, 4.9, 4.7, 4.6, 4.3, 4.2, 4.1, 4.0, 4.0, 4.0,
//				4.4);
//		mockKlineList = mockHighestPrice.stream().map(price -> {
//			KlineDto kline = new KlineDto();
//			kline.setHighestprice(price);
//			return kline;
//		}).collect(Collectors.toList());
//		tdStructures = Arrays
//				.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isReversal(false).build());
//		klineService.isStructuresPerfect(mockKlineList, tdStructures);
 
		List<Double> mockOpenPositionHighestPriceList = Arrays.asList(15.9, 16.1, 15.6, 15.8, 15.8, 20.6, 15.4, 15.7,
				16.3, 16.1, 16.1, 16.2, 16.4, 16.3);
		List<Double> mockOpenPositionLowestPriceList = Arrays.asList(14.9, 15.2, 14.7, 14.9, 15.1, 15.0, 14.9, 14.6,
				15.2, 15.4, 15.3, 14.1, 15.9, 14.7);
		List<Double> mockOpenPositionOpenPriceList = Arrays.asList(15.4, 15.8, 14.9, 15.7, 15.2, 15.3, 15.2, 15.5, 16.1,
				15.8, 15.6, 15.3, 16.1, 15.1);
		List<Double> mockOpenPositionClosePriceList = Arrays.asList(15.0, 15.6, 15.1, 15.2, 15.6, 15.4, 15.1, 15.3,
				15.2, 15.6, 15.7, 15.8, 16.2, 15.4);
		Integer mockSize = mockOpenPositionHighestPriceList.size();
		List<KlineDto> mockOpenPositionKlineList = new ArrayList<KlineDto>();
		for (int i = 0; i < mockSize; i++) {
			double highestPrice = mockOpenPositionHighestPriceList.get(i);
			double lowestPrice = mockOpenPositionLowestPriceList.get(i);
			double openPrice = mockOpenPositionOpenPriceList.get(i);
			double closePrice = mockOpenPositionClosePriceList.get(i);

			KlineDto kline = new KlineDto();
			kline.setOpenprice(openPrice);
			kline.setCloseprice(closePrice);
			kline.setHighestprice(highestPrice);
			kline.setLowestprice(lowestPrice);
			mockOpenPositionKlineList.add(kline);
		}
		tdStructures = Arrays.asList(KlineTdStructure.builder().index(5).isStructureComplete(true).isPerfect(true)
				.isPerfectComplete(true).isReversal(true).build());
		klineService.isStructuresSatisfyOpenPosition(mockOpenPositionKlineList, tdStructures);
	}
}
