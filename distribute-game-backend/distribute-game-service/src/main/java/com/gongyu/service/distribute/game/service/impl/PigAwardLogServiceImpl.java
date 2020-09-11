package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongyu.service.distribute.game.common.enums.IsClickBuyEnum;
import com.gongyu.service.distribute.game.common.enums.OpenResultEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.PigAwardLogMapper;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogPageDto;
import com.gongyu.service.distribute.game.model.dto.PigAwardLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigAwardLog;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.service.distribute.game.service.PigAwardLogService;
import com.gongyu.service.distribute.game.service.PigGoodsService;
import com.gongyu.service.distribute.game.service.PigReservationService;
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
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PigAwardLogServiceImpl extends CrudServiceSupport<PigAwardLogMapper, PigAwardLog> implements PigAwardLogService  {

    @Autowired
    private PigAwardLogMapper awardLogMapper;
    @Autowired
    private PigReservationService reservationService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PigGoodsService goodsService;
    @Override
    public IPage<PigAwardLogPageDto> queryPigAwardLog(IPage<PigAwardLogPageDto> page,PigAwardLogPageDto dto) {
        List<PigAwardLogPageDto> list = awardLogMapper.findPage(page, dto);
        list.forEach(e ->{
            PigGoods goods = goodsService.getOne(new QueryWrapper<PigGoods>().eq("id", e.getPigId()));
            List<PigReservation> res = reservationService.list(new QueryWrapper<PigReservation>().eq("reservation_scene", e.getId()));
            convertResultDto(e,goods,res);

        });
        page.setRecords(list);
        return page;
    }

    @SneakyThrows
    public void convertResultDto(PigAwardLogPageDto e, PigGoods goods, List<PigReservation> res) {
        //是否是遗留数据 （遗留数据预约记录与中奖纪录无法关联）
        if(CollectionUtils.isEmpty(res)){
            log.info("中奖纪录列表 convertResultDto res is null 兼容老数据 dto:{},res:{}",objectMapper.writeValueAsString(e),objectMapper.writeValueAsString(res));
            e.setBeforePerson(0);
            e.setClickPerson(0);
        }else{
            e.setBeforePerson(res.size());
            List<PigReservation> list = res.stream()
                    .filter(item -> item.getIsClickBuy() == IsClickBuyEnum.TRUE.getCode())
                    .collect(Collectors.toList());
            e.setClickPerson(list.size());
        }
        e.setGoodsName(goods.getGoodsName());
        //全部参与人数
        int robPerson = getNum(e.getJoinUserList());
        //中奖人数
        int luckyPerson =getNum(e.getAwardUserList());
        e.setRobPerson(robPerson);
        e.setLuckyPerson(luckyPerson);
        //计算中奖率保留两位小数
        if(0 == luckyPerson){
            e.setLuckyChance("0.00%");
        }else{
            BigDecimal luckyChance = new BigDecimal(String.valueOf(luckyPerson)).divide(new BigDecimal(String.valueOf(robPerson)),2,BigDecimal.ROUND_HALF_UP);
            luckyChance = luckyChance.multiply(new BigDecimal("100"));
            e.setLuckyChance(luckyChance + "%");
        }
        e.setDrawTime(DateUtils.format(DateUtil.getDate(e.getChangeTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));

    }

    public static void main(String[] args) {
        new BigDecimal("0").divide(new BigDecimal("0"));
    }

    public Integer getNum(String nums){
        if(StringUtils.isNotBlank(nums)){
            return nums.split(",").length;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigAwardLog(PigAwardLogSaveDto pigAwardLogSaveDto) {
        PigAwardLog pigAwardLog = new PigAwardLog();
        BeanUtils.copyProperties(pigAwardLogSaveDto, pigAwardLog);
        this.save(pigAwardLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigAwardLog(PigAwardLogModifyDto pigAwardLogModifyDto) {
        PigAwardLog pigAwardLog = new PigAwardLog();
        BeanUtils.copyProperties(pigAwardLogModifyDto, pigAwardLog);
        this.updateById(pigAwardLog);
    }

    @Override
    public void handleAwardLog(PigAwardLog awardLog, Set<Long> joinUsers, Set<Long> awardUsers){
        awardLog.setAwardUserList(StringUtils.join(awardUsers,","));
        awardLog.setJoinUserList(StringUtils.join(joinUsers,","));
        awardLog.setChangeTime(DateUtil.getNowDate());
        awardLog.setOpenResult(OpenResultEnum.OEPN.getCode());
    }
}