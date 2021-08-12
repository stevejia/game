package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futures.common.enums.*;
import com.futures.common.utils.DateUtil;
import com.futures.manager.DelayQueueManager;
import com.futures.manager.PigOrderManager;
import com.futures.manager.RevealLuckyManager;
import com.futures.manager.UserExclusivePigManager;
import com.futures.mapper.PigAppealMapper;
import com.futures.model.DelayTask;
import com.futures.model.dto.PigAppealModifyDto;
import com.futures.model.dto.PigAppealSaveDto;
import com.futures.model.entity.PigAppeal;
import com.futures.model.entity.PigOrder;
import com.futures.model.entity.UserExclusivePig;
import com.futures.model.entity.Users;
import com.futures.service.PigAppealService;
import com.futures.service.PigOrderService;
import com.futures.service.UsersService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PigAppealServiceImpl extends CrudServiceSupport<PigAppealMapper, PigAppeal> implements PigAppealService {

    @Autowired
    private PigOrderService pigOrderService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private UserExclusivePigManager pigManager;
    @Autowired
    private DelayQueueManager queueManager;
    @Autowired
    private RevealLuckyManager luckyManager;

    @Override
    public IPage<PigAppeal> queryPigAppeal(IPage<PigAppeal> page, @Valid @ModelAttribute PigAppealModifyDto pigAppealModifyDto) {
        if (!StringUtils.isEmpty(pigAppealModifyDto.getOrderNo())) {
            PigOrder one = pigOrderService.getOne(Wrappers.<PigOrder>lambdaQuery().eq(PigOrder::getPigOrderSn, pigAppealModifyDto.getOrderNo()));
            pigAppealModifyDto.setOrderId(one.getId());
        }
        if (!StringUtils.isEmpty(pigAppealModifyDto.getMobile())) {
            Users users = usersService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, pigAppealModifyDto.getMobile()));
            pigAppealModifyDto.setUserId(users.getId());
        }
        LambdaQueryWrapper<PigAppeal> eq = Wrappers.<PigAppeal>lambdaQuery();
        if (pigAppealModifyDto.getUserId() != null) {
            eq.eq(PigAppeal::getUserId, pigAppealModifyDto.getUserId());
        }
        if (pigAppealModifyDto.getStatus() != null) {
            eq.eq(PigAppeal::getStatus, pigAppealModifyDto.getStatus());
        }
        if (pigAppealModifyDto.getOrderId() != null) {
            eq.eq(PigAppeal::getOrderId, pigAppealModifyDto.getOrderId());
        }
        eq.orderByDesc(PigAppeal::getId);
        // 申诉人手机号
        IPage<PigAppeal> page1 = this.page(page, eq);
        List<PigAppeal> records = page1.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return page1;
        }
        List<Long> collect = records.stream().map(PigAppeal::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return page1;
        }
        Collection<Users> users = usersService.listByIds(collect);
        if (CollectionUtils.isEmpty(users)) {
            return page1;
        }
        Map<Long, String> map = users.stream().collect(Collectors.toMap(Users::getId, Users::getMobile));
        records.stream().forEach(e -> e.setMobile(map.get(e.getUserId())));
        //  被申诉人手机号
        records.stream().forEach(e -> {
            PigOrder byId = orderManager.getById(e.getOrderId());
            Long userId;
            if (e.getComplainant() == 1) {
                userId = byId.getSellUserId();
            } else {
                userId = byId.getPurchaseUserId();
            }
            Users byId1 = usersService.getById(userId);
            e.setOtherMobile(byId1.getMobile());
            // 返回订单信息
            PigOrder one = pigOrderService.getOne(Wrappers.<PigOrder>lambdaQuery().eq(PigOrder::getOrderId, e.getOrderId()));
            e.setOrderNo(one.getPigOrderSn());
            e.setAddTime(e.getAddTime() * 1000);
            e.setImgUrl(byId.getImgUrl());
        });
        return page1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigAppeal(PigAppealSaveDto pigAppealSaveDto) {
        PigAppeal pigAppeal = new PigAppeal();
        BeanUtils.copyProperties(pigAppealSaveDto, pigAppeal);
        this.save(pigAppeal);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigAppeal(PigAppealModifyDto pigAppealModifyDto) {
        PigAppeal pigAppeal = new PigAppeal();
        BeanUtils.copyProperties(pigAppealModifyDto, pigAppeal);
        pigAppeal.setUpdateTime(DateUtil.getNowDate());
        this.updateById(pigAppeal);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void authPigAppeal(PigAppealModifyDto pigAppealModifyDto) {
        PigAppeal pigAppeal = new PigAppeal();
        BeanUtils.copyProperties(pigAppealModifyDto, pigAppeal);
        pigAppeal.setUpdateTime(DateUtil.getNowDate());
        PigOrder order = orderManager.getById(pigAppealModifyDto.getOrderId());
        UserExclusivePig pig = pigManager.findById(order.getPigId());
        PigAppeal appeal = this.getOne(new QueryWrapper<PigAppeal>().eq("order_id", order.getOrderId()));
        appeal.setStatus(appeal.getStatus());

        if (AppealStatusEnum.APPEAL_PASS.getCode() == pigAppeal.getStatus()) {
        	//买家申诉
            if (1 == pigAppeal.getComplainant().intValue()) {
                pig.setUserId(pigAppeal.getUserId());
                order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                order.setBuyConfirmStatus(CommEnum.TRUE.getCode());
                order.setSellConfirmStatus(CommEnum.TRUE.getCode());
            } else { //卖家申诉成功
                pig.setUserId(pigAppeal.getUserId());
                pig.setIsAbleSale(SaleStatusEnum.TRUE.getCode());

                order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                order.setUserId(pigAppeal.getUserId());
                order.setPurchaseUserId(pigAppeal.getUserId());
                order.setSellConfirmStatus(CommEnum.TRUE.getCode());
            }
            this.refeshTask(order);
        } else {
        	//申诉的其他状态 重启订单
            if(1 == pigAppeal.getComplainant().intValue()){
                order.setPayStatus(PayStatusEnum.PROCESSING.getCode());
            }else{
                order.setPayStatus(PayStatusEnum.PROCESSING.getCode());
            }
            //刷新订单确认的任务
            this.refeshTask(order);
        }
        this.updateById(appeal);
        pigManager.update(pig);
        orderManager.updateIgnoreNull(order);

        this.updateById(pigAppeal);
    }

    /**
     * 审核通过刷新取消订单时间，延长2小时
     *
     * @param order
     */
    public void refeshTask(PigOrder order) {
        DelayTask task = RedisUtils.get("task:order-" + order.getPigOrderSn());
        queueManager.remove(task);
        //删除老的延时取消订单，并重新计算延时时间加入一份新的取消订单的延时任务
        task = luckyManager.convertTaskBase(order, DelayTaskEnum.CANCEL_ORDER, 7200000L);
        queueManager.put(task);
        RedisUtils.remove("task:order-" + order.getPigOrderSn());
        RedisUtils.set("task:order-" + order.getPigOrderSn(), task, 7200000L);
    }
}