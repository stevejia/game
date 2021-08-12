package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futures.common.enums.IsClickBuyEnum;
import com.futures.common.enums.OpenResultEnum;
import com.futures.common.utils.DateUtil;
import com.futures.common.utils.OrderUtil;
import com.futures.mapper.PigAwardLogMapper;
import com.futures.model.dto.PigAwardLogModifyDto;
import com.futures.model.dto.PigAwardLogPageDto;
import com.futures.model.dto.PigAwardLogSaveDto;
import com.futures.model.dto.UserExclusivePigDTO;
import com.futures.model.entity.PigAwardLog;
import com.futures.model.entity.PigGoods;
import com.futures.model.entity.PigReservation;
import com.futures.model.entity.UserExclusivePig;
import com.futures.service.PigAwardLogService;
import com.futures.service.PigGoodsService;
import com.futures.service.PigReservationService;
import com.futures.service.UserExclusivePigService;
import com.futures.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.util.DateUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PigAwardLogServiceImpl extends CrudServiceSupport<PigAwardLogMapper, PigAwardLog>
		implements PigAwardLogService {

	@Autowired
	private PigAwardLogMapper awardLogMapper;
	@Autowired
	private PigReservationService reservationService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private PigGoodsService goodsService;
	@Autowired
	private UserExclusivePigService userExclusivePigService;
	@Override
	public IPage<PigAwardLogPageDto> queryPigAwardLog(IPage<PigAwardLogPageDto> page, PigAwardLogPageDto dto) {
		List<PigAwardLogPageDto> list = awardLogMapper.findPage(page, dto);
		list.forEach(e -> {
			PigGoods goods = goodsService.getOne(new QueryWrapper<PigGoods>().eq("id", e.getPigId()));
			List<PigReservation> res = reservationService
					.list(new QueryWrapper<PigReservation>().eq("reservation_scene", e.getId()));
			convertResultDto(e, goods, res);

		});
		page.setRecords(list);
		return page;
	}

	@Override
	public List<PigAwardLogPageDto> queryPigGoodsSummary(PigAwardLogPageDto dto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Long drawTimeMilli = LocalDateTime.parse(dto.getDrawTime(), formatter).toInstant(ZoneOffset.of("+8"))
				.toEpochMilli() / 1000;
		List<PigAwardLog> list = awardLogMapper
				.selectList(new QueryWrapper<PigAwardLog>().ge("change_time", drawTimeMilli));
		List<PigGoods> pigGoodsList = goodsService.list(new QueryWrapper<PigGoods>().eq("is_display", 1));
		List<PigReservation> pigReservationList = reservationService
				.list(new QueryWrapper<PigReservation>().ge("reservation_time", drawTimeMilli));

		List<UserExclusivePig> userAllPigList = userExclusivePigService
				.list(new QueryWrapper<UserExclusivePig>().eq("is_pig_lock", 0));
//		List<UserExclusivePig> userExclusivePigDTOList = userExclusivePigService
//				.list(new QueryWrapper<UserExclusivePig>().eq("is_able_sale", 1).eq("is_pig_lock", 0));
		List<UserExclusivePig> userExclusivePigDTOList = userAllPigList.stream().filter(pig -> pig.getIsAbleSale() == 1)
				.collect(Collectors.toList());
		List<PigAwardLogPageDto> pigAwardLogPageList = new ArrayList<PigAwardLogPageDto>();

		List<Long> userList = RedisUtils2.getList("robProduct:");
		pigGoodsList.forEach(pigGoods -> {
			Long pigGoodsId = pigGoods.getId();
			PigAwardLog pigAwardLog = null;

			List<PigAwardLog> matchedPigAwardLogs = list.stream().filter(lst -> lst.getPigId() == pigGoodsId)
					.collect(Collectors.toList());
			if (matchedPigAwardLogs != null && matchedPigAwardLogs.size() > 0) {
				pigAwardLog = matchedPigAwardLogs.get(0);
			}

			List<PigReservation> matchedPigReservationList = pigReservationList.stream()
					.filter(pr -> pr.getPigId() == pigGoodsId).collect(Collectors.toList());
			List<UserExclusivePig> matchedUserAllExclusivePigDTOList = userAllPigList.stream()
					.filter(uep -> uep.getPigId() == pigGoodsId).collect(Collectors.toList());
			List<UserExclusivePig> matchedUserExclusivePigDTOList = userExclusivePigDTOList.stream()
					.filter(uep -> uep.getPigId() == pigGoodsId).collect(Collectors.toList());
			PigAwardLogPageDto newDto = convertResultDto(pigAwardLog, pigGoods, matchedPigReservationList,
					matchedUserExclusivePigDTOList, matchedUserAllExclusivePigDTOList);
			newDto.setRubingPersons(userList);
			pigAwardLogPageList.add(newDto);
		});

		return pigAwardLogPageList;
	}

	@SneakyThrows
	public PigAwardLogPageDto convertResultDto(PigAwardLog pigAwardLog, PigGoods goods, List<PigReservation> pigResList,
			List<UserExclusivePig> uepList, List<UserExclusivePig> uaepList) {
		PigAwardLogPageDto dto = new PigAwardLogPageDto();
		// 全部参与人数
		int robPerson = 0;
		int luckyPerson = 0;
		if (pigAwardLog != null) {

			// 全部参与人数
			robPerson = getNum(pigAwardLog.getJoinUserList());
			// 中奖人数
			luckyPerson = getNum(pigAwardLog.getAwardUserList());
			dto.setAwardUserList(pigAwardLog.getAwardUserList());
			dto.setJoinUserList(pigAwardLog.getJoinUserList());
		}
		dto.setRobPerson(robPerson);
		dto.setLuckyPerson(luckyPerson);

		String luckyChanceStr = "0.00%";
		if (0 != luckyPerson) {

			BigDecimal luckyChance = new BigDecimal(String.valueOf(luckyPerson))
					.divide(new BigDecimal(String.valueOf(robPerson)), 2, BigDecimal.ROUND_HALF_UP);
			luckyChance = luckyChance.multiply(new BigDecimal("100"));
			luckyChanceStr = luckyChance + "%";
		}
		dto.setLuckyChance(luckyChanceStr);

		dto.setBeforePerson(pigResList.size());

		List<PigReservation> clickPigResList = pigResList.stream()
				.filter(item -> item.getIsClickBuy() == IsClickBuyEnum.TRUE.getCode()).collect(Collectors.toList());
		dto.setClickPerson(clickPigResList.size());

		dto.setCanSalePig(uepList.size());
		dto.setAllPig(uaepList.size());
		dto.setGoodsName(goods.getGoodsName());
		dto.setPigId(goods.getId().intValue());
		dto.setDrawTime(DateUtils.format(goods.getEndTime(), DateUtils.DEFAULT_DATE_TIME_FORMAT));
		dto.setSmallPrice(goods.getSmallPrice());
		dto.setLargePrice(goods.getLargePrice());

		dto.setReservation(goods.getReservation());
		dto.setAdoptiveEnergy(goods.getAdoptiveEnergy());
		dto.setContractDays(goods.getContractDays());
		dto.setIncomeRatio(goods.getIncomeRatio());
		return dto;
	}

	@SneakyThrows
	public void convertResultDto(PigAwardLogPageDto e, PigGoods goods, List<PigReservation> res) {
		// 是否是遗留数据 （遗留数据预约记录与中奖纪录无法关联）
		if (CollectionUtils.isEmpty(res)) {
			log.info("中奖纪录列表 convertResultDto res is null 兼容老数据 dto:{},res:{}", objectMapper.writeValueAsString(e),
					objectMapper.writeValueAsString(res));
			e.setBeforePerson(0);
			e.setClickPerson(0);
		} else {
			e.setBeforePerson(res.size());
			List<PigReservation> list = res.stream()
					.filter(item -> item.getIsClickBuy() == IsClickBuyEnum.TRUE.getCode()).collect(Collectors.toList());
			e.setClickPerson(list.size());
		}
		e.setGoodsName(goods.getGoodsName());
		// 全部参与人数
		int robPerson = getNum(e.getJoinUserList());
		// 中奖人数
		int luckyPerson = getNum(e.getAwardUserList());
		e.setRobPerson(robPerson);
		e.setLuckyPerson(luckyPerson);
		// 计算中奖率保留两位小数
		if (0 == luckyPerson) {
			e.setLuckyChance("0.00%");
		} else {
			BigDecimal luckyChance = new BigDecimal(String.valueOf(luckyPerson))
					.divide(new BigDecimal(String.valueOf(robPerson)), 2, BigDecimal.ROUND_HALF_UP);
			luckyChance = luckyChance.multiply(new BigDecimal("100"));
			e.setLuckyChance(luckyChance + "%");
		}
		e.setDrawTime(DateUtils.format(DateUtil.getDate(e.getChangeTime()), DateUtils.DEFAULT_DATE_TIME_FORMAT));

	}

	public static void main(String[] args) {
		new BigDecimal("0").divide(new BigDecimal("0"));
	}

	public Integer getNum(String nums) {
		if (StringUtils.isNotBlank(nums)) {
			return nums.split(",").length;
		}
		return 0;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void savePigAwardLog(PigAwardLogSaveDto pigAwardLogSaveDto) {
		PigAwardLog pigAwardLog = new PigAwardLog();
		BeanUtils.copyProperties(pigAwardLogSaveDto, pigAwardLog);
		this.save(pigAwardLog);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void modifyPigAwardLog(PigAwardLogModifyDto pigAwardLogModifyDto) {
		PigAwardLog pigAwardLog = new PigAwardLog();
		BeanUtils.copyProperties(pigAwardLogModifyDto, pigAwardLog);
		this.updateById(pigAwardLog);
	}

	@Override
	public void handleAwardLog(PigAwardLog awardLog, Set<Long> joinUsers, Set<Long> awardUsers) {
		awardLog.setAwardUserList(StringUtils.join(awardUsers, ","));
		awardLog.setJoinUserList(StringUtils.join(joinUsers, ","));
		awardLog.setChangeTime(DateUtil.getNowDate());
		awardLog.setOpenResult(OpenResultEnum.OEPN.getCode());
	}
}