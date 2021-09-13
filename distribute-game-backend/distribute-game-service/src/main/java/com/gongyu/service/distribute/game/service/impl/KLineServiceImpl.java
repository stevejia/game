package com.gongyu.service.distribute.game.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.gongyu.service.distribute.game.common.utils.TupleUtil.FourTuple;
import com.gongyu.service.distribute.game.common.utils.TupleUtil.ThreeTuple;
import com.gongyu.service.distribute.game.common.utils.TupleUtil.TwoTuple;
import com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper;
import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineExample;
import com.gongyu.service.distribute.game.model.entity.KlineOpenPosition;
import com.gongyu.service.distribute.game.model.entity.KlineTdStructure;
import com.gongyu.service.distribute.game.model.entity.Rb2110Kline;
import com.gongyu.service.distribute.game.service.KlineService;
import com.gongyu.service.distribute.game.utils.BeanCopyUtils;
import com.gongyu.service.distribute.game.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KLineServiceImpl extends CrudServiceSupport<Rb2110KlineMapper, Rb2110Kline> implements KlineService {

	@Autowired
	private Rb2110KlineMapper rb2110KlineMapper;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public List<KlineDto> queryRbKLine(KlineExample params) {

//		String classFullName = "com.gongyu.service.distribute.game.mapper.Rb2110KlineMapper";
		RedisUtils2.remove("rb2110_30_td");

		List<KlineDto> klineList = this.queryKline(params, "rb2110", 30, "selectByExample", KlineDto.class);
		this.processReversal(klineList, 0);
		return null;
	}

	@Override
	public List<KlineDto> queryRbKLine2(KlineExample params, String tableName) {
		List<KlineDto> klineList = this.queryKline(params, tableName, 30, "selectByExample", KlineDto.class);
		return klineList;
	}

	@Override
	public List<KlineDto> queryKline(KlineExample params, String tableName, int period) {
		List<KlineDto> klineList = this.queryKline(params, tableName, period, "selectByExample", KlineDto.class);
		return klineList;
	}

	@Override
	public void processKlineTD(String instrumentId, int period) {
		KlineExample param = new KlineExample();
		param.createCriteria().andPeriodEqualTo(period);

		List<KlineDto> klineList = this.queryKline(param, instrumentId, period, "selectByExample", KlineDto.class);
		this.processKlineTd(klineList, 0);
	}

	private void processKlineTd(List<KlineDto> kLineList, int index) {
		Map<Integer, Boolean> reversalMap = this.processReversal(kLineList, index);
		List<KlineTdStructure> tdStructures = this.processTdStructures(kLineList, reversalMap);
		this.isStructuresPerfect(kLineList, tdStructures);
		List<KlineOpenPosition> openPositions = this.isStructuresSatisfyOpenPosition(kLineList, tdStructures);
		if (kLineList != null && kLineList.size() > 0) {
			KlineDto kline = kLineList.get(0);
			String instrumentId = kline.getInstrumentid();
			Integer period = kline.getPeriod(); 
			String redisKey = instrumentId + "_" + period + "_td";
			RedisUtils2.set(redisKey, JSONObject.toJSONString(tdStructures));

			String openPositionKey = instrumentId + "_" + period + "_op";
			RedisUtils2.remove(openPositionKey);
			RedisUtils2.set(openPositionKey, JSONObject.toJSONString(openPositions));
		}
	}

	/**
	 * 熊市或牛市反转逻辑 满足熊市或牛市反转 则进入到下一步
	 * 
	 * @param kLineList  k线list
	 * @param startIndex K线开始索引
	 */
	@Override
	public Map<Integer, Boolean> processReversal(List<KlineDto> kLineList, int index) {

		final Map<Integer, Boolean> reversalResult = new HashMap<Integer, Boolean>();

		int klineSize = kLineList.size();
		int startIndex = index;
		for (; startIndex < klineSize; startIndex++) {
			// k线列表为空 或者 当前k线后如果没有6根线 则不需要继续
			if (startIndex + 6 > kLineList.size()) {

				continue;
			}
			// 取当前k线索引后的6根k线 用以计算牛(熊)市反转
			List<KlineDto> sixList = kLineList.subList(startIndex, startIndex + 6);

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
				log.info("满足熊市反转：K1价格{}<K5价格{},k2价格{}>k6价格{}", k1ClosePrice, k5ClosePrice, k2ClosePrice, k6ClosePrice);
				reversalResult.put(startIndex + 5, true);
			} else
			// 熊市反转条件 如果k1.closePrice > k5.closePrice & k2.closePrice < k6.closePrice
			if (k1ClosePrice > k5ClosePrice && k2ClosePrice < k6ClosePrice) {
				reversalResult.put(startIndex + 5, false);
				log.info("满足牛市反转：K1价格{}>K5价格{},k2价格{}<k6价格{}", k1ClosePrice, k5ClosePrice, k2ClosePrice, k6ClosePrice);
			}
		}

		return reversalResult;

//		log.info("执行耗时：" + String.valueOf(new Date().getTime() - startTime));
//		for(KlineTdStructure item: tdStructureList) {
//			TwoTuple<Boolean, Boolean> result =  this.isTDPerfect(kLineList, item.getIndex(), item.isReversal());
//			item.setPerfect(result.getSecond());
//			item.setPerfectComplete(result.getSecond());
//		}
	}

	private List<KlineTdStructure> getTdStructures(String instrumentId, int period) {
		String redisTDKey = instrumentId + "_" + period + "_td";
		String json = RedisUtils2.get(redisTDKey);
		List<KlineTdStructure> tdStructures = null;
		if (json != null) {
			tdStructures = JSONObject.parseArray(json, KlineTdStructure.class);
		}

		return tdStructures;
	}

	private void setTdStructures(String instrumentId, int period, List<KlineTdStructure> tdStructures) {
		List<KlineTdStructure> rediStructures = this.getTdStructures(instrumentId, period);

		String redisTDKey = instrumentId + "_" + period + "_td";
		RedisUtils2.set(redisTDKey, redisTDKey);
	}

	private List<KlineTdStructure> combineStructures(List<KlineTdStructure> rediStructures,
			List<KlineTdStructure> tdStructures) {
		if (rediStructures == null) {
			return tdStructures;
		}
		return null;
	}

	@Override
	public List<KlineTdStructure> processTdStructures(List<KlineDto> klineList, Map<Integer, Boolean> reversalMap) {
		if (reversalMap == null) {
			return null;
		}
		List<KlineTdStructure> tdStructures = new ArrayList<KlineTdStructure>();
		reversalMap.keySet().forEach(startIndex -> {
			Boolean isReversal = reversalMap.get(startIndex);
			KlineTdStructure tdStructure = this.processTD(klineList, isReversal, startIndex);
			if (tdStructure != null) {
				tdStructures.add(tdStructure);
			}
		});
		return tdStructures;
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
	private KlineTdStructure processTD(List<KlineDto> kLineList, boolean isReversal, int startIndex) {

		KlineTdStructure tdStructure = null;
		// 如果klineList为空 或者 startIndex开始之后没有9根k线 结束此td结构尝试
		if (kLineList == null) {
			return tdStructure;
		}

		// 是否满足td结构的情况 需要持续关注 不满9跟k线的时候也需要去匹配 因为存在动态k线的情况
		int endIndex = startIndex + 9 > kLineList.size() ? kLineList.size() : startIndex + 9;

		// startIndex-4表示找到 第一根K线的4天前的k线 startIndex+9表示 第一根k线后的9根k线 总共14根
		List<KlineDto> thirteenList = kLineList.subList(startIndex - 4, endIndex);
		boolean isTDStructure = true;
		// 循环thirteenList startIndex初始化成4表示当前比较的K线在第五条
		int newStartIndex = 4;
		for (int i = newStartIndex; i < thirteenList.size(); i++) {

			KlineDto currentKline = thirteenList.get(i);
			KlineDto fourDayAgoKline = thirteenList.get(i - 4);

			// 当前K线的收盘价
			double currentClosePrice = currentKline.getCloseprice();
			Double fourDayAgoClosePrice = fourDayAgoKline.getCloseprice();

			isTDStructure = isTDStructure && this.comparePrice(currentClosePrice, fourDayAgoClosePrice, isReversal);
		}
		List<KlineDto> tdStructureList = thirteenList.subList(4, thirteenList.size());

		if (isTDStructure) {
			boolean isCompletedTDStructure = tdStructureList.size() == 9;

			String klineTimes = tdStructureList.stream().map(item -> item.getKlineTime())
					.collect(Collectors.joining(","));
			KlineDto startKline = tdStructureList.get(0);

			tdStructure = KlineTdStructure.builder().index(startIndex).klineTime(startKline.getKlineTime())
					.klineTimes(klineTimes).isReversal(isReversal).isStructureComplete(isCompletedTDStructure)
					.instrumentId(startKline.getInstrumentid()).period(startKline.getPeriod()).build();
			log.info("满足td{}结构{}", isReversal ? "买入" : "卖出",
					isCompletedTDStructure ? "有9根k线" : "只有" + tdStructureList.size() + "根k线");
			if (isCompletedTDStructure) {
				KlineDto k0Line = kLineList.get(startIndex - 1);
				// td结构开始k线的前一天收盘价
				KlineDto k1Line = thirteenList.get(0);
				double k1LowestPrice = k1Line.getLowestprice();
				double k1HighestPrice = k1Line.getHighestprice();
				double k0ClosePrice = k0Line.getCloseprice();
				double supportPrice = Math.min(k0ClosePrice, k1LowestPrice);
				double pressurePrice = Math.max(k0ClosePrice, k1HighestPrice);
				tdStructure.setSupportPrice(supportPrice);
				tdStructure.setPressurePrice(pressurePrice);
			}

		} else {
			log.info("不满足td{}结构", isReversal ? "买入" : "卖出");
		}
		return tdStructure;
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

	@Override
	public void isStructuresPerfect(List<KlineDto> klineList, List<KlineTdStructure> tdStructures) {
		for (KlineTdStructure item : tdStructures) {
			if (item.isStructureComplete()) {
				TwoTuple<Boolean, Boolean> result = this.isTDPerfect(klineList, item.getIndex(), item.isReversal());
				item.setPerfect(result.getFirst());
				item.setPerfectComplete(result.getSecond());
			}
		}
	}

	/**
	 * 计算TD结构是否完善
	 * 
	 * @param klineList
	 * @param startIndex
	 * @param isReversal
	 * @return <Boolean, Boolean> first, second first: 是否完善 second: TD结构完善是否完成
	 */
	private FourTuple<Boolean, Boolean, Double, Double> isTDPerfect(List<KlineDto> klineList, int startIndex,
			boolean isReversal) {

		FourTuple<Boolean, Boolean, Double, Double> result = new FourTuple<Boolean, Boolean, Double, Double>(false,
				false, null, null);

		int klineLen = klineList.size();

		if (klineList == null || klineList.size() < startIndex + 9) {
			return result;
		}

		int endIndex = startIndex + 13 > klineLen ? klineLen : startIndex + 13;

		List<KlineDto> thirteenList = klineList.subList(startIndex, endIndex);
		if (thirteenList.size() == 13) {
			result.setSecond(true);
			log.info("后续有13根k线 当前TD结构完善 完成");
		}
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
//				log.info("买入结构完善" + startIndex);
//				this.isSatisfyOpenPosition(klineList, startIndex, isReversal);
				result.setFirst(true);
				result.setSecond(true);
				log.info("TD买入结构完善");
			} else {
				log.info("TD买入结构不完善");
			}

			return result;
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
//			log.info("卖出结构完善" + startIndex);
			KlineDto k1Line = thirteenList.get(0);
			double k1LowestPrice = k1Line.getLowestprice();
			double k1HighestPrice = k1Line.getHighestprice();
			result.setFirst(true);
			result.setSecond(true);
			log.info("TD买出结构完善");
		} else {
			log.info("TD买出结构不完善");
		}
		return result;
	}

	@Override
	public List<KlineOpenPosition> isStructuresSatisfyOpenPosition(List<KlineDto> klineList,
			List<KlineTdStructure> tdStructures) {
		List<KlineOpenPosition> openPositions = new ArrayList<KlineOpenPosition>();

		for (KlineTdStructure item : tdStructures) {
			if (item.isPerfect() && item.isPerfectComplete()) {
				KlineOpenPosition openPosition = this.isSatisfyOpenPosition(klineList, item.getIndex(),
						item.isReversal());
				if (openPosition != null) {
					openPositions.add(openPosition);
				}
			}
		}

		return openPositions;
	}

	private final double diffNum = 0.5;

	private KlineOpenPosition isSatisfyOpenPosition(List<KlineDto> klineList, int startIndex, boolean isReversal) {
		KlineOpenPosition openPosition = null;
		// 计算TD买入结构支撑线(价格)
		List<KlineDto> tenList = klineList.subList(startIndex - 1, startIndex + 9);

		KlineDto kline1 = tenList.get(1);

		KlineDto klineYesterdayDto = tenList.get(0);

		double yesterdayClosePrice = klineYesterdayDto.getCloseprice();

		double kline1LowestPrice = kline1.getLowestprice();

		// 趋势支撑线
		double supportPrice = Math.min(yesterdayClosePrice, kline1LowestPrice);

		double kline1HighestPrice = kline1.getHighestprice();

		// 趋势压力线
		double pressurePrice = Math.max(yesterdayClosePrice, kline1HighestPrice);

//		log.info("趋势支撑线价格：" + lowestPrice);

		double tdLowestPrice = Double.MAX_VALUE;
		int tdLowestPriceIndex = 0;

		boolean upperLowestPrice = true;

		boolean belowHighestPrice = true;
		double tdHighestPrice = Double.MIN_VALUE;
		int tdHighestPriceIndex = 0;
		for (int i = 1; i < tenList.size(); i++) {
			KlineDto kline = tenList.get(i);
			double klineClosePrice = kline.getCloseprice();

			if (isReversal) {
				// 任一一根k线的收盘价都不低于趋势支撑线对应价格
				if (klineClosePrice < supportPrice) {
					upperLowestPrice = false;
					break;
				}

				double klineLowestPrice = kline.getLowestprice();

				if (tdLowestPrice > klineLowestPrice) {
					tdLowestPriceIndex = i;
				}

				tdLowestPrice = Math.min(tdLowestPrice, klineLowestPrice);
			} else {
				double klineHighestPrice = kline.getHighestprice();
				if (klineClosePrice > pressurePrice) {
					belowHighestPrice = false;
					break;
				}

				if (tdHighestPrice < klineHighestPrice) {
					tdHighestPriceIndex = i;
				}
				tdHighestPrice = Math.max(tdHighestPrice, klineHighestPrice);
			}
		}
		KlineDto kline9 = tenList.get(9);
		String instrumentId = kline9.getInstrumentid();
		Integer period = kline9.getPeriod();
		if (upperLowestPrice && isReversal) {

			double kline9ClosePrice = kline9.getCloseprice();
			// 第9根k线收盘价极其靠近趋势支撑线
			boolean isNearSupport = Math.abs(kline9ClosePrice - supportPrice) < this.diffNum;
			if (isNearSupport) {
				KlineDto lowestPriceKline = tenList.get(tdLowestPriceIndex);
				KlineDto lpYesterdayKline = tenList.get(tdLowestPriceIndex - 1);
				double lpYesterdayClosePrice = lpYesterdayKline.getCloseprice();
				double lpLowestPrice = lowestPriceKline.getLowestprice();
				double lpHighestPrice = lowestPriceKline.getHighestprice();
				double realWave = Math.max(lpHighestPrice - lpLowestPrice, lpHighestPrice - lpYesterdayClosePrice);
				realWave = Math.max(realWave, lpYesterdayClosePrice - lpLowestPrice);
				double realLowestPrice = Math.min(lpLowestPrice, lpYesterdayClosePrice);

				double stopLossBuyPrice = realLowestPrice - realWave;

				boolean canOpenBuy = Math
						.abs((kline9ClosePrice - pressurePrice) / (kline9ClosePrice - stopLossBuyPrice)) > 1.5;
				if (canOpenBuy) {
					openPosition = KlineOpenPosition.builder().instrumentId(instrumentId).period(period)
							.commodityName("test品种").openTime("3333").openPrice(stopLossBuyPrice)
							.stopLossPrice(stopLossBuyPrice).openType(0).build();
				}
			}
		}

		if (belowHighestPrice && !isReversal) {
			double kline9ClosePrice = kline9.getCloseprice();
			// 第9根k线收盘价极其靠近趋势压力线
			boolean isNearSupport = Math.abs(kline9ClosePrice - pressurePrice) < this.diffNum;
			if (isNearSupport) {
				KlineDto hpKline = tenList.get(tdHighestPriceIndex);
				KlineDto hpYesterdayKline = tenList.get(tdHighestPriceIndex - 1);
				double hpYesterdayClosePrice = hpYesterdayKline.getCloseprice();
				double hpLowestPrice = hpKline.getLowestprice();
				double hpHighestPrice = hpKline.getHighestprice();
				double realWave = Math.max(hpHighestPrice - hpLowestPrice, hpHighestPrice - hpYesterdayClosePrice);
				realWave = Math.max(realWave, hpYesterdayClosePrice - hpLowestPrice);
				double realHighestPrice = Math.max(hpHighestPrice, hpYesterdayClosePrice);

				double stopLossSalePrice = realHighestPrice + realWave;

				boolean canOpenSale = Math
						.abs((kline9ClosePrice - supportPrice) / (kline9ClosePrice - stopLossSalePrice)) > 1.5;
				if (canOpenSale) {
					openPosition = KlineOpenPosition.builder().instrumentId(instrumentId).period(period)
							.commodityName("test品种").openTime("3333").openPrice(stopLossSalePrice)
							.stopLossPrice(stopLossSalePrice).openType(1).build();
				}

			}
		}

		return openPosition;
	}

	public <D, K, M> List<D> queryKline(K k, String instrumentId, int period, String methodName, Class<D> dClass) {
		// 合约Id_周期作为redis缓存的key
		String redisKlineKey = instrumentId + "_" + period;
		List<D> dtoList = this.getRedisKline(redisKlineKey, dClass);
		if (dtoList != null && dtoList.size() > 0) {
			return dtoList;
		}
		try {
			String packageStr = "com.gongyu.service.distribute.game";
			String firstStr = instrumentId.substring(0, 1);
			String mapperName = instrumentId.replaceFirst(firstStr, firstStr.toLowerCase()) + "KlineMapper";

			String exampleCls = packageStr + ".model.entity."
					+ instrumentId.replaceFirst(firstStr, firstStr.toUpperCase()) + "KlineExample";

			Class<?> ExampleClass = Class.forName(exampleCls);

			Field mapperField = this.getClass().getDeclaredField(mapperName);
			Object mapper = mapperField.get(this);

			Method method = mapper.getClass().getMethod(methodName, ExampleClass);

			List<?> entities = (List<?>) method.invoke(mapper, BeanCopyUtils.copyObject(k, ExampleClass));
			dtoList = new ArrayList<D>();

			BeanCopyUtils.copyList(entities, dtoList, dClass);
			Gson gson = new Gson();
			RedisUtils2.set(redisKlineKey, gson.toJson(dtoList));
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoList;

	}

	private <D> List<D> getRedisKline(String redisKlineKey, Class<D> cls) {
		List<D> klineList = null;
		String json = RedisUtils2.get(redisKlineKey);

//		JsonConvert.DeserializeObject<JsonData<List<Students>>>(json)
		if (json != null) {
			klineList = JSONObject.parseArray(json, cls);

//			Gson gson = new Gson();
//			JsonElement jsonEl = new JsonParser().parse(json);
//			klineList = (List<D>)gson.fromJson(jsonEl, new TypeToken<List<D>>() {
//			}.getType());
//			klineList = (List<D>) json;
		}
		return klineList;
	}

	private <D> void getRedisKline(String redisKlineKey, List<D> dtoList) {
		Gson gson = new Gson();
		RedisUtils2.set(redisKlineKey, gson.toJson(dtoList));
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
	public ThreeTuple<List<KlineDto>, List<KlineTdStructure>, List<KlineOpenPosition>> refreshKline(KlineDto queryParams) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
//
//		String formatDate = dateFormat.format(new Date());
//		List<KlineDto> newKlineList = new ArrayList<KlineDto>();
//		String originFormatDate = RedisUtils2.get("rb2110_real_time");
//		if (!formatDate.equals(originFormatDate)) {
//			String newKlineString = this.mockRedisKline("rb2110", formatDate);
//			RedisUtils2.set("rb2110_real_60", newKlineString);
//			RedisUtils2.set("rb2110_real_time", formatDate);
//			newKlineList.add(this.generateKline(newKlineString));
//		}
		
		String instrumentId = queryParams.getInstrumentid();
		Integer period = queryParams.getPeriod();

		String redisTdKey = instrumentId + "_"+period+"_td";

		String redisTdJson = RedisUtils2.get(redisTdKey);
		

		List<KlineTdStructure> tdStructures = JSONObject.parseArray(redisTdJson, KlineTdStructure.class);

		String openPositionKey = instrumentId + "_"+period + "_op";
		
		String redisOpJson = RedisUtils2.get(openPositionKey);
		List<KlineOpenPosition> openPositions = JSONObject.parseArray(redisOpJson, KlineOpenPosition.class);
		ThreeTuple<List<KlineDto>, List<KlineTdStructure>, List<KlineOpenPosition>> result = new ThreeTuple<List<KlineDto>, List<KlineTdStructure>, List<KlineOpenPosition>>(
				new ArrayList<KlineDto>(), tdStructures, openPositions);

		return result;
	}

	private String mockRedisKline(String instrumentId, String dateFormat) {
		double lowPrice = 5200.00;
		double highPrice = 5280.0;
		double diffPrice = highPrice - lowPrice;

		Random rand = new Random();

		double openPrice = lowPrice + diffPrice * rand.nextDouble();
		double closePrice = lowPrice + diffPrice * rand.nextDouble();

		double lowestPrice = lowPrice + diffPrice * rand.nextDouble();

		double highestPrice = lowestPrice + diffPrice / 2 * rand.nextDouble();

		int turnover = rand.nextInt(1000);

		String[] klineItems = new String[] { instrumentId, dateFormat + "00", String.valueOf(dateFormat),
				String.valueOf(openPrice), String.valueOf(closePrice), String.valueOf(highestPrice),
				String.valueOf(lowestPrice), String.valueOf(turnover), "60" };

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

	@Override
	public void processKlineTD(List<KlineDto> klineList, int startIndex) {
		this.processKlineTd(klineList, startIndex);
	}

}
