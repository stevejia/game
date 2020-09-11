package com.gongyu.service.distribute.game.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.model.dto.AdoptRecordDto;
import com.gongyu.service.distribute.game.model.dto.MyUserDataDto;
import com.gongyu.service.distribute.game.model.dto.ReserveResultDto;
import com.gongyu.service.distribute.game.model.dto.TransfResultDto;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.PigOrderService;
import com.gongyu.service.distribute.game.service.UserLevelService;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 11:36
 */
@Service
public class MyManager {

    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private UserExclusivePigManager pigManager;
    @Autowired
    private PigOrderService orderService;

    public AdoptRecordDto convertAdoptResult(PigOrder order, PigGoods goods, UserExclusivePig pig){
        AdoptRecordDto dto = new AdoptRecordDto();
        dto.setOrderNo(null == order ? StringUtils.EMPTY : order.getPigOrderSn());
        //分裂精灵没有对应订单，给一个默认成功的
        dto.setOrderStatus(null == order ? PayStatusEnum.SUCCESS.getCode() : order.getPayStatus());
        dto.setBuyComfirm(null == order ? CommEnum.TRUE.getCode() : order.getBuyConfirmStatus());
        dto.setSellComfirm(null == order ? CommEnum.TRUE.getCode() : order.getSellConfirmStatus());
        BigDecimal rate = goods.getIncomeRatio().divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
        BigDecimal income = pig.getPrice().multiply(rate);
        dto.setIncome(income);
        dto.setPigName(goods.getGoodsName());
        dto.setPigUrl(goods.getImages());
        dto.setSmallPrice(goods.getSmallPrice());
        dto.setLargePrice(goods.getLargePrice());
        dto.setContractDay(goods.getContractDays());
        dto.setContractRate(goods.getIncomeRatio());
        if(null == order){
            dto.setContractTime(DateUtils.format(DateUtil.getDate(pig.getBuyTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        }else{
            dto.setContractTime(DateUtils.format(DateUtil.getDate(order.getEstablishTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        }
        dto.setPayImageUrl(null == order ?  StringUtils.EMPTY : order.getImgUrl());
        dto.setGoodsPrice(pig.getPrice());
        return dto;
    }

    public MyUserDataDto convertUserDataResult(Users users,List<UserExclusivePig> list){
        MyUserDataDto dataDto = new MyUserDataDto();
        BigDecimal asset = new BigDecimal("0");
        dataDto.setUserId(Long.valueOf(users.getId()));
        dataDto.setUserNmae(users.getNickname());
        dataDto.setAvatarUrl(HeadImgEnum.URL.getImgUrl());
        dataDto.setMobile(users.getMobile());
        dataDto.setPoints(users.getPayPoints());
        Integer level = users.getLevel();
        if(null != level){
            UserLevel userLevel = userLevelService.getById(level);
            dataDto.setLevelName(userLevel.getLevelName());
        }
        for(UserExclusivePig pig : list){
            PigOrder order = orderService.getOne(new QueryWrapper<PigOrder>().eq("order_id",pig.getOrderId()));
            if(null == order || (null != order && order.getSellConfirmStatus() == CommEnum.TRUE.getCode())
                    || users.getId().longValue() == order.getSellUserId()){
                asset = asset.add(null == pig.getPrice() ? new BigDecimal("0") : pig.getPrice());
            }

        }
        dataDto.setAsset(asset);
        dataDto.setAccumulIncome(users.getAccumulatedIncome());
        dataDto.setRecomIncome(users.getRecomIncome());
        return dataDto;
    }

    public ReserveResultDto convertReserveResult(PigGoods goods, PigReservation reservation){
        ReserveResultDto resultDto = new ReserveResultDto();
        resultDto.setPigName(goods.getGoodsName());
        resultDto.setReserveDate(DateUtils.format(DateUtil.getDate(reservation.getReservationTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        if(IsClickBuyEnum.FALSE.getCode().intValue() == reservation.getIsClickBuy()){
            resultDto.setRobStatus("预约成功-待抢");
        }else {
            resultDto.setRobStatus(RobStatusEnum.parse(reservation.getReservationStatus()).getDesc());
        }
        resultDto.setPayPoints(reservation.getPayPoints());
        return resultDto;
    }

    public TransfResultDto convertTransf(PigOrder order,PigGoods goods,PigAppeal appeal,String peopleName){
        TransfResultDto resultDto = new TransfResultDto();
        resultDto.setOrderNo(order.getPigOrderSn());
        resultDto.setPigName(goods.getGoodsName());
        resultDto.setPigUrl(goods.getImages());
        resultDto.setContractDay(goods.getContractDays());
        resultDto.setContractTime(DateUtils.format(DateUtil.getDate(order.getEstablishTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        resultDto.setSmallPrice(goods.getSmallPrice());
        resultDto.setLargePrice(goods.getLargePrice());
        resultDto.setContractRate(goods.getIncomeRatio());
        resultDto.setOrderStatus(order.getPayStatus());
        resultDto.setBuyComfirm(order.getBuyConfirmStatus());
        resultDto.setSellComfirm(order.getSellConfirmStatus());
        resultDto.setBuyName(peopleName);
        resultDto.setPayImageUrl(order.getImgUrl());
        resultDto.setGoodsPrice(order.getPigPrice());
        if(null != appeal){
            resultDto.setAppealDesc(appeal.getRemark());
            resultDto.setAppealDate(DateUtils.format(DateUtil.getDate(appeal.getAddTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        }
        return resultDto;
    }

    public TransfResultDto convertTransfByEtc(UserExclusivePig pig,PigGoods goods,PigOrder order){
        TransfResultDto resultDto = new TransfResultDto();
        resultDto.setOrderNo(null == order ? "" : order.getPigOrderSn());
        resultDto.setPigName(goods.getGoodsName());
        resultDto.setPigUrl(goods.getImages());
        resultDto.setSmallPrice(goods.getSmallPrice());
        resultDto.setLargePrice(goods.getLargePrice());
        resultDto.setContractDay(goods.getContractDays());
        resultDto.setContractRate(goods.getIncomeRatio());
        resultDto.setContractTime(DateUtils.format(DateUtil.getDate(pig.getBuyTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT));
        resultDto.setGoodsPrice(null == pig ? new BigDecimal("0") : pig.getPrice());
        return resultDto;
    }
}
