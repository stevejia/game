package com.gongyu.service.distribute.game.service.impl;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper;
import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineExample;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.model.entity.Rb2110KlineExample;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.service.distribute.game.utils.BeanCopyUtils;
import com.gongyu.service.distribute.game.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KLineServiceImpl extends CrudServiceSupport<Rb2110KlineMapper, Rb2110Kline> implements KlineService {

	@Autowired
	private Rb2110KlineMapper rb2110KlineMapper;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public List<KlineDto> queryRbKLine(Rb2110KlineExample params) {

		String classFullName = "com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper";

		try {

			Class<?> cls = Class.forName(classFullName);

			Field field = this.getClass().getDeclaredField("rb2110KlineMapper");

			Object mapper = field.get(this);

//			List<KlineDto> dtoList2 = this.queryKline(params, mapper, "selectByExample", KlineDto.class);

			List<Rb2110Kline> rb2110KLines = rb2110KlineMapper.selectByExample(params);

			List<KlineDto> dtoList = new ArrayList<KlineDto>();

			BeanCopyUtils.copyList(rb2110KLines, dtoList, KlineDto.class);
			// List<KlineDto> dtoList = MockData.mockTDBuyDataNineKLine();
			// 要比较4天前的收盘价 所以索引从第4天开始
			// 不然没有比较的必要
			this.processReversal(dtoList, 5);
			return dtoList;
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<KlineDto> queryRbKLine2(KlineExample params, String tableName) {
		 List<KlineDto> klineList = this.queryKline(params, tableName, "selectByExample", KlineDto.class);
		 return klineList;
	}

	/**
	 * 熊市或牛市反转逻辑 满足熊市或牛市反转 则进入到下一步
	 * 
	 * @param kLineList  k线list
	 * @param startIndex K线开始索引
	 */

	private void processReversal(List<KlineDto> kLineList, int startIndex) {
		// 当前k线后如果没有6根线 则不需要继续
		if (kLineList == null || kLineList.size() < 6 || startIndex > kLineList.size() - 7) {
			return;
		}

		// 取当前k线索引后的6跟k线
		List<KlineDto> sixList = kLineList.subList(startIndex - 5, startIndex + 1);

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

			boolean isTDBuy = this.processTD(kLineList, true, startIndex);
			// 如果满足TD买入结构条件
			if (isTDBuy) {
				log.info("满足TD买入结构" + startIndex);
			}

		}
		// 熊市反转条件 如果k1.closePrice >< k5.closePrice & k2.closePrice < k6.closePrice
		if (k1ClosePrice > k5ClosePrice && k2ClosePrice < k6ClosePrice) {

			log.info("牛市反转" + startIndex);

			boolean isTDSale = this.processTD(kLineList, false, startIndex);

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
		if (kLineList == null || kLineList.size() < 9 || kLineList.size() - 10 < startIndex) {
			return false;
		}

		// startIndex-5表示找到 第一根K线的4天前的k线 startIndex+9表示 第一根k线后的9根k线 总共14根
		List<KlineDto> fourteenList = kLineList.subList(startIndex - 4, startIndex + 10);
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
			this.isTDPerfect(kLineList, startIndex, isReversal);
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

	private boolean isTDPerfect(List<KlineDto> klineList, int startIndex, boolean isReversal) {
		int klineLen = klineList.size();

		if (klineList == null || klineList.size() < startIndex + 9) {
			return false;
		}

		int endIndex = startIndex + 13 > klineLen ? klineLen : startIndex + 13;

		List<KlineDto> thirteenList = klineList.subList(startIndex, endIndex);

		// 获取满足TD结构的第6和第7根k线
		KlineDto kline6 = thirteenList.get(5);
		KlineDto kline7 = thirteenList.get(6);

		/**
		 * 熊市反转的情况下 计算买入TD结构是否完善
		 */
		if (isReversal) {
			/**
			 * 从第8根k线开始只要有一根k线满足：kn.lowestPrice < min(k6.lowestPrice, k7.lowestPrice)
			 */
			// 比较第6根和第7根k线最低价，取最小值
			// min(k6.lowestPrice, k7.lowestPrice)
			double kline6LowestPrice = kline6.getLowestprice();
			double kline7LowestPrice = kline7.getLowestprice();
			double lowestPrice = Math.min(kline6LowestPrice, kline7LowestPrice);

			boolean isLowestPrice = false;

			for (int i = 7; i < thirteenList.size(); i++) {
				KlineDto kline = thirteenList.get(i);
				double klineLowestPrice = kline.getLowestprice();
				isLowestPrice = isLowestPrice || klineLowestPrice < lowestPrice;
			}

			if (isLowestPrice) {
				log.info("买入结构完善" + startIndex);
				this.isSatisfyOpenPosition(klineList, startIndex, isReversal);
			}

			return isLowestPrice;
		}
		/**
		 * 从第8根k线开始只要有一根k线满足：kn.highestPrice > max(k6.highestPrice, k7.highestPrice)
		 */
		double kline6HighestPrice = kline6.getHighestprice();
		double kline7HighestPrice = kline7.getHighestprice();
		double highestPrice = Math.max(kline6HighestPrice, kline7HighestPrice);

		boolean isHighestPrice = false;

		for (int i = 7; i < thirteenList.size(); i++) {
			KlineDto kline = thirteenList.get(i);
			double klineHighestPrice = kline.getHighestprice();
			isHighestPrice = isHighestPrice || klineHighestPrice > highestPrice;
		}

		if (isHighestPrice) {
			log.info("卖出结构完善" + startIndex);
		}
		return isHighestPrice;
	}

	private boolean isSatisfyOpenPosition(List<KlineDto> klineList, int startIndex, boolean isReversal) {
		// 计算TD买入结构支撑线(价格)
		List<KlineDto> tenList = klineList.subList(startIndex - 1, startIndex + 9);

		KlineDto kline1 = tenList.get(1);

		KlineDto klineYesterdayDto = tenList.get(0);

		double yesterdayClosePrice = klineYesterdayDto.getCloseprice();

		double kline1LowestPrice = kline1.getLowestprice();

		double lowestPrice = Math.min(yesterdayClosePrice, kline1LowestPrice);

		log.info("趋势支撑线价格：" + lowestPrice);

		boolean upperLowestPrice = true;
		for (int i = 1; i < tenList.size(); i++) {
			KlineDto kline = tenList.get(i);
			double klineClosePrice = kline.getCloseprice();

			if (klineClosePrice < lowestPrice) {
				upperLowestPrice = false;
				break;
			}
		}

		if (upperLowestPrice) {
			log.info("不低于支撑线" + startIndex);

		}

		return false;
	}

	public <D, K, M> List<D> queryKline(K k, String tableName, String methodName, Class<D> dClass) {
		List<D> dtoList = null;
		try {
			String packageStr = "com.gongyu.service.distribute.game";
			String firstStr = tableName.substring(0, 1);
			String mapperName = tableName.replaceFirst(firstStr, firstStr.toLowerCase()) + "Mapper";

			String exampleCls = packageStr + ".model.entity." + tableName + "Example";

			Class<?> ExampleClass = Class.forName(exampleCls);

			Field mapperField = this.getClass().getDeclaredField(mapperName);
			Object mapper = mapperField.get(this);

			Method method = mapper.getClass().getMethod(methodName, ExampleClass);

			List<?> entities = (List<?>) method.invoke(mapper, BeanCopyUtils.copyObject(k, ExampleClass));
			dtoList = new ArrayList<D>();

			BeanCopyUtils.copyList(entities, dtoList, dClass);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoList;

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

	@Override
	public List<KlineDto> refreshKline(KlineExample params) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		String formatDate = dateFormat.format(new Date());
		List<KlineDto> newKlineList = new ArrayList<KlineDto>();
		String originFormatDate = RedisUtils2.get("rb2110_real_time");
		if(!formatDate.equals(originFormatDate)) {
			String newKlineString = this.mockRedisKline("rb2110", formatDate);
			RedisUtils2.set("rb2110_real_60", newKlineString);
			RedisUtils2.set("rb2110_real_time", formatDate);
			newKlineList.add(this.generateKline(newKlineString));
		}
		return newKlineList;
	}
	
	private String mockRedisKline(String instrumentId, String dateFormat) {
		double lowPrice = 5200.00;
		double highPrice = 5280.0;
		double diffPrice = highPrice - lowPrice;
		
		Random rand = new Random();
		
		double openPrice = lowPrice + diffPrice * rand.nextDouble();
		double closePrice = lowPrice + diffPrice* rand.nextDouble();
		
		double lowestPrice = lowPrice + diffPrice * rand.nextDouble();
		
		double highestPrice = lowestPrice + diffPrice/2*rand.nextDouble();
		
		int turnover = rand.nextInt(1000);
		
		String[] klineItems = new String[] {instrumentId, dateFormat+"00", String.valueOf(dateFormat), String.valueOf(openPrice), String.valueOf(closePrice), String.valueOf(highestPrice), String.valueOf(lowestPrice), String.valueOf(turnover), "60" };
		
		
		
		String klineString = String.join(",", klineItems);
		
		return klineString;
	}
	
	private KlineDto generateKline(String klineString) {
		KlineDto kline = new KlineDto();
		String[] klineItems = klineString.split(",");
		kline.setInstrumentid(klineItems[0]);
		kline.setLocalTime(klineItems[1]);
		kline.setKlineTime(klineItems[2]);
		kline.setOpenprice(Double.valueOf(klineItems[3]));
		kline.setCloseprice(Double.valueOf(klineItems[4]));
		kline.setHighestprice(Double.valueOf(klineItems[5]));
		kline.setLowestprice(Double.valueOf(klineItems[6]));
		kline.setVolume(Integer.valueOf(klineItems[7]));
		kline.setPeriod(Integer.valueOf(klineItems[8]));
		return kline;
	}

}
