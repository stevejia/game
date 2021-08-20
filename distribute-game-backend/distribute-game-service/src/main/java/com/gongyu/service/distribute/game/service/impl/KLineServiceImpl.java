package com.gongyu.service.distribute.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import net.bytebuddy.asm.Advice.This;
import oracle.jdbc.Const;

@Service
@Slf4j
public class KLineServiceImpl extends CrudServiceSupport<Rb2110KlineMapper, Rb2110Kline> implements KlineService {

	@Autowired
	private Rb2110KlineMapper rb2110KlineMapper;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public List<KlineDto> queryRbKLine(Rb2110KlineExample params) {
		List<Rb2110Kline> rb2110KLines = rb2110KlineMapper.selectByExample(params);

		List<KlineDto> dtoList = new ArrayList<KlineDto>();

		BeanCopyUtils.copyList(rb2110KLines, dtoList, KlineDto.class);
		// List<KlineDto> dtoList = MockData.mockTDBuyDataNineKLine();
		// 要比较4天前的收盘价 所以索引从第4天开始
		// 不然没有比较的必要
		this.processReversal(dtoList, 4);
		return dtoList;
	}

	/**
	 * 熊市或牛市反转逻辑 满足熊市或牛市反转 则进入到下一步
	 * 
	 * @param kLineList  k线list
	 * @param startIndex K线开始索引
	 */

	private void processReversal(List<KlineDto> kLineList, int startIndex) {
		// 当前k线后如果没有6根线 则不需要继续
		if (kLineList == null || kLineList.size() < 6 || startIndex > kLineList.size() - 6) {
			return;
		}

		// 取当前k线索引后的6跟k线
		List<KlineDto> sixList = kLineList.subList(startIndex - 4, startIndex + 2);

		// k1线对应list的最后一个元素
		KlineDto k1 = sixList.get(5);
		// k2线对应list的倒数第二个元素
		KlineDto k2 = sixList.get(4);
		// k5线对应list的第二个元素
		KlineDto k5 = sixList.get(1);
		// k6线对应list的第一个元素
		KlineDto k6 = sixList.get(0);

		// 获取k1、k2、k5、k6的收盘价 closePrice
		double k1ClosePrice = k1.getCloseprice();
		double k2ClosePrice = k2.getCloseprice();
		double k5ClosePrice = k5.getCloseprice();
		double k6ClosePrice = k6.getCloseprice();

		// 熊市反转条件 如果k1.closePrice < k5.closePrice & k2.closePrice > k6.closePrice

		if (k1ClosePrice < k5ClosePrice && k2ClosePrice > k6ClosePrice) {

			log.info("熊市反转" + startIndex);

			boolean isTDBuy = this.processTD(kLineList, true, startIndex + 2);
			// 如果满足TD买入结构条件
			if (isTDBuy) {
				log.info("满足TD买入结构" + startIndex);
			}

		}
		// 熊市反转条件 如果k1.closePrice >< k5.closePrice & k2.closePrice < k6.closePrice
		if (k1ClosePrice > k5ClosePrice && k2ClosePrice < k6ClosePrice) {

			log.info("牛市反转" + startIndex);

			boolean isTDSale = this.processTD(kLineList, false, startIndex + 2);

			// 如果满足TD卖出结构条件
			if (isTDSale) {
				log.info("满足TD卖出结构" + startIndex);
			}
		}

		// 按照索引递增依次跑下去
		this.processReversal(kLineList, ++startIndex);

	}

	/**
	 * 查找TD买入/卖出结构
	 * 
	 * @param kLineList  k线列表
	 * @param isReversal 是否熊市反转
	 * @param startIndex 起始k线位置
	 * 
	 * @return boolean 是否满足TD 买入or卖出结构
	 */
	private boolean processTD(List<KlineDto> kLineList, boolean isReversal, int startIndex) {
		if (kLineList == null || kLineList.size() < 9 || kLineList.size() - 9 < startIndex) {
			return false;
		}

		// startIndex-5表示找到 第一根K线的4天前的k线 startIndex+9表示 第一根k线后的9根k线 总共14根
		List<KlineDto> fourteenList = kLineList.subList(startIndex - 5, startIndex + 9);
		boolean isTDStructure = true;
		// 循环thirteenList startIndex初始化成4表示当前比较的K线在第五条
		int newStartIndex = 4;
		for (int i = newStartIndex; i < newStartIndex + 9; i++) {

			KlineDto currentKline = fourteenList.get(i);
			KlineDto fourDayAgoKline = fourteenList.get(i - 4);

			// 当前K线的收盘价
			double currentClosePrice = currentKline.getCloseprice();
			Double fourDayAgoClosePrice = fourDayAgoKline.getCloseprice();

			isTDStructure = isTDStructure && this.comparePrice(currentClosePrice, fourDayAgoClosePrice, isReversal);
		}
		if (isTDStructure) {
			String klineIds = fourteenList.stream().map(kline -> {
				return kline.getId().toString();
			}).collect(Collectors.joining(","));
			log.info("(" + klineIds + ")");
		}
		return isTDStructure;
	}

	/**
	 * 比较价格 熊市反转时（买入结构）当前K线的收盘价比4天前的收盘价低 返回true 否则返回false 牛市反转是 (卖出结构)
	 * 当前k线的收盘价比4天前的收盘价高 返回true 否则返回false
	 * 
	 * @param currentClosePrice    当前要比较K线的收盘价
	 * @param fourDayAgoClosePrice 4天前的收盘价
	 * @param isReversal           熊市反转（买入结构）
	 * @return
	 */
	private boolean comparePrice(double currentClosePrice, double fourDayAgoClosePrice, boolean isReversal) {
		// 熊市反转 TD买入结构条件 当前K线的收盘价低于4天前的收盘价
		if (isReversal) {
			return currentClosePrice < fourDayAgoClosePrice;
		}

		// 牛市反转 TD买入结构条件 当前K线的收盘价高于4天前的收盘价
		return currentClosePrice > fourDayAgoClosePrice;
	}

	public static class MockData {

		public static List<KlineDto> mockTDBuyDataNineKLine() {
			List<Double> mockClosePriceList = Arrays.asList(10.0, 11.0, 12.0, 13.0, 14.0, 9.9, 9.8, 9.7, 9.6, 9.5, 9.4,
					9.3, 9.2, 9.1, 9.0);

			List<KlineDto> mockKlineList = mockClosePriceList.stream().map(price -> {
				KlineDto kline = new KlineDto();
				kline.setCloseprice(price);
				return kline;
			}).collect(Collectors.toList());

			return mockKlineList;
		}
	}

}
