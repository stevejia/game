package com.gongyu.service.distribute.game.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.OrderTypeEnum;
import com.gongyu.service.distribute.game.common.enums.PayStatusEnum;
import com.gongyu.service.distribute.game.common.enums.SaleStatusEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.common.utils.OrderUtil;
import com.gongyu.service.distribute.game.mapper.PigOrderMapper;
import com.gongyu.service.distribute.game.model.dto.AdoptReqDto;
import com.gongyu.service.distribute.game.model.dto.TransfReqDto;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 20:28
 */
@Slf4j
@Service
public class PigOrderManager {

    @Autowired
    private PigOrderMapper orderMapper;
    @Autowired
    private OrderUtil orderUtil;
    @Autowired
    private UserExclusivePigManager pigManager;

    public List<PigOrder> findPageByUser(IPage page, Long userId, AdoptReqDto param){
        return orderMapper.findPageByUser(page,userId,param);
    }

    public List<PigOrder> findPageBySell(IPage page,Long userId,TransfReqDto param){
        return orderMapper.findPageBySell(page,userId,param);
    }

    public PigOrder findByBuyUser(Long userId){
        return orderMapper.findByBuyUser(userId);
    }

    public PigOrder findBySellUser(Long userId){
        return orderMapper.findBySellUser(userId);
    }

    public PigOrder convert(Long userId,Long sellUser,PayStatusEnum payStatusEnum, OrderTypeEnum orderTypeEnum,UserExclusivePig pig){
        PigOrder order = new PigOrder();

        order.setPigOrderSn(orderUtil.grenOrderNo());
        order.setEstablishTime(DateUtil.getNowDate());
        order.setPayStatus(payStatusEnum.getCode());
        order.setSellUserId(sellUser);
        order.setPurchaseUserId(userId);
        order.setPigLevel(pig.getPigId());
        order.setPigPrice(pig.getPrice());
        order.setPigId(pig.getId());
        order.setImgUrl(StringUtils.EMPTY);
        order.setEndTime(DateUtil.getNowDate());
        order.setType(orderTypeEnum.getCode());
        order.setUserId(userId);
        order.setAppealTime(0L);
        order.setPaynum("");
        return order;
    }

    public PigOrder insert(PigOrder order){
        orderMapper.insert(order);
        return order;
    }

    public void insertForeach(List<PigOrder> list){
        orderMapper.insertForeach(list);
    }

    public PigOrder getByOrderNo(String orderNo){
        return orderMapper.getByOrderNo(orderNo);
    }

    public PigOrder getById(Long id){
        return orderMapper.selectById(id);
    }

    public void updateIgnoreNull(PigOrder order){
        orderMapper.updateById(order);
    }

    /**
     * 创建中奖者订单
     * @param users 所有抢购用户
     * @param pigs
     * @return
     */
    @Transactional
    public List<PigOrder> createOrder(List<Long> users,List<UserExclusivePig> pigs){
        List<PigOrder> orders = new ArrayList<>();
        List<UserExclusivePig> dealerPigs = new ArrayList<>();
        List<Long> dealerUsers = new ArrayList<>();
        //找出所有被指定过的木材
        Iterator<UserExclusivePig> iterator = pigs.iterator();
        while (iterator.hasNext()){
            UserExclusivePig next = iterator.next();
            if(null != next.getAppointUserId() && 0 != next.getAppointUserId()){
                dealerPigs.add(next);
                dealerUsers.add(next.getAppointUserId());
                users.remove(next.getAppointUserId());
                iterator.remove();
            }
        }
        //创建普通订单
        for(int i = 0; i < users.size(); i++){
            UserExclusivePig pig = pigs.get(i);
            pigManager.updateSale(pig,SaleStatusEnum.FALSE);
            PigOrder order = this.convert(users.get(i),pig.getUserId(),PayStatusEnum.PROCESSING,OrderTypeEnum.NORMAL_POSSESSION,pig);
            PigOrder insert = insert(order);
            pig.setOrderId(insert.getOrderId());
            pigManager.update(pig);
            orders.add(order);
            log.info("开奖流程 生成抢购普通订单 userId:{},order:{}",pigs.get(i).getUserId(),JSON.toJSON(order));
        }
        //创建庄家指定订单
        for(int i = 0; i < dealerUsers.size(); i++){
            UserExclusivePig pig = dealerPigs.get(i);
            pigManager.updateSale(pig,SaleStatusEnum.FALSE);
            PigOrder order = this.convert(pig.getAppointUserId(),pig.getUserId(),PayStatusEnum.PROCESSING,OrderTypeEnum.NORMAL_POSSESSION,pig);
            PigOrder insert = this.insert(order);
            pig.setOrderId(insert.getOrderId());
            pigManager.update(pig);
            orders.add(order);
            log.info("开奖流程 生成抢购商家指定订单 userId:{},order:{}",dealerPigs.get(i).getUserId(),JSON.toJSON(order));
        }
        return orders;
    }
}
