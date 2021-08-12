package com.futures.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.enums.CommEnum;
import com.futures.common.enums.OpenResultEnum;
import com.futures.common.enums.PayStatusEnum;
import com.futures.common.utils.DateUtil;
import com.futures.manager.PigOrderManager;
import com.futures.mapper.PigOrderMapper;
import com.futures.model.dto.OrderDetailsDto;
import com.futures.model.dto.PigOrderModifyDto;
import com.futures.model.dto.PigOrderSaveDto;
import com.futures.model.dto.TranComfirmeReqDto;
import com.futures.model.entity.PigAwardLog;
import com.futures.model.entity.PigOrder;
import com.futures.model.entity.UserPayment;
import com.futures.model.entity.Users;
import com.futures.service.*;
import com.futures.utils.DateUtils;
import com.futures.utils.RedisUtils2;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PigOrderServiceImpl extends CrudServiceSupport<PigOrderMapper, PigOrder> implements PigOrderService {

    @Autowired
    private PigOrderMapper orderMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserPaymentService paymentService;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private PigAwardLogService awardLogService;
    @Autowired
    private SysSmsFpi sysSmsFpi;

    @Override
    public IPage<PigOrderModifyDto> queryPigOrder(IPage<PigOrderModifyDto> page, PigOrderModifyDto dto) {
        List<PigOrderModifyDto> list = orderMapper.findPage(page, dto);
        return page.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigOrder(PigOrderSaveDto pigOrderSaveDto) {
        PigOrder pigOrder = new PigOrder();
        BeanUtils.copyProperties(pigOrderSaveDto, pigOrder);
        this.save(pigOrder);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigOrder(PigOrderModifyDto pigOrderModifyDto) {
        PigOrder pigOrder = new PigOrder();
        BeanUtils.copyProperties(pigOrderModifyDto, pigOrder);
        this.updateById(pigOrder);
    }

    @Override
    public BaseResponse luckStatus(Long userId, Long goodsId) {
    	List<PigOrder> orders = RedisUtils2.get("newOrders:" + goodsId);
    	if(orders == null) {
    		return BaseResponse.newInstance("2").setDetail("正在开奖中");
    	}
    	
    	PigOrder order = null;
    	
    	for(PigOrder po: orders) {
    		if(po.getPurchaseUserId().intValue() == userId.intValue()) {
    			order = po;
    		}
    	}
    	
    	
//        PigAwardLog awardLog = awardLogService.getOne(new QueryWrapper<PigAwardLog>().eq("pig_id", goodsId).eq("open_result", OpenResultEnum.OEPN.getCode()).orderByDesc("id"));
//        log.info("获取中奖纪录信息 awardLog:{}", JSON.toJSONString(awardLog));
//        if (null == awardLog) {
//            return BaseResponse.newInstance("2").setDetail("正在开奖中");
//        }
//        Date date = DateUtil.getDate(DateUtil.getNowDate());
//        String dateFormatToString = DateUtils.getDateFormatToString(date, DateUtils.DATE_DEFAULT_FORMAT);
//        Date date1 = DateUtil.getDate(awardLog.getChangeTime());
//        String dateFormatToString1 = DateUtils.getDateFormatToString(date1, DateUtils.DATE_DEFAULT_FORMAT);
//        if (null != awardLog && OpenResultEnum.NOT_OPEN.getCode() == awardLog.getOpenResult() && dateFormatToString.equals(dateFormatToString1)) {
//            return BaseResponse.newInstance("2").setDetail("正在开奖中");
//        }
//
//        PigOrder order = orderMapper.getOrderByUser(userId, goodsId);
//        log.info("中奖者订单 order:{}", JSON.toJSONString(order));
        if (null == order) {
            return BaseResponse.error("没有抢到该商品");
        }
        BaseResponse baseResponse = BaseResponse.success(order).setDetail("抢购成功");
        return baseResponse;
    }

    @Override
    public BaseResponse orderDetails(String orderNo) {
        PigOrder order = orderMapper.getByOrderNo(orderNo);
        Users purchaseUser = usersService.getById(order.getPurchaseUserId());
        Users sellUser = usersService.getById(order.getSellUserId());
        List<UserPayment> paymentList = paymentService.list(new QueryWrapper<UserPayment>()
                .eq("user_id", order.getSellUserId())
                .eq("status", CommEnum.TRUE.getCode()));
        
        List<UserPayment> buyPaymentList = paymentService.list(new QueryWrapper<UserPayment>()
                .eq("user_id", order.getPurchaseUserId())
                .eq("status", CommEnum.TRUE.getCode()));
        Assert.notEmpty(paymentList, "收款人未添加收款方式");
        Assert.notNull(order, "订单不存在");
        Assert.notNull(purchaseUser, "没有找到购买人");
        Assert.notNull(sellUser, "没有找到出售人");
        OrderDetailsDto detailsDto = convert(order, purchaseUser, sellUser, paymentList);
        String contactMobile = "";
        for(UserPayment pay: buyPaymentList) {
        	if(pay.getPaysalt() != null) {
        		contactMobile = pay.getPaysalt();
        	}
        }
        detailsDto.setContactMobile(contactMobile);
        return BaseResponse.success(detailsDto);
    }

    /**
     * 装配返回信息
     *
     * @param order        订单
     * @param purchaseUser 购买人
     * @param sellUser     出售人
     * @return
     */
    public OrderDetailsDto convert(PigOrder order, Users purchaseUser, Users sellUser, List<UserPayment> paymentLogList) {
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setOrderTime(DateUtil.getDate(order.getEstablishTime()));
        dto.setTransferName(sellUser.getNickname());
        dto.setTransferMobile(sellUser.getMobile());
        dto.setAdoptName(purchaseUser.getNickname());
        dto.setAdoptMobile(purchaseUser.getMobile());
        dto.setMoney(order.getPigPrice());
        dto.setPayments(paymentLogList);
        dto.setPayImageUrl(order.getImgUrl());
        return dto;
    }

    @Override
    public BaseResponse tranComfirme(TranComfirmeReqDto param, Long userId) {
        PigOrder order = orderManager.getByOrderNo(param.getOrderNo());
        Assert.notNull(order, "没有找到订单");
        if (PayStatusEnum.FREEZE.getCode().intValue() == order.getPayStatus().intValue()) {
            return BaseResponse.error("该订单已冻结");
        }
        if (order.getSellUserId().longValue() == userId.longValue()) {
            order.setSellConfirmStatus(CommEnum.TRUE.getCode());
        } else if (order.getPurchaseUserId().longValue() == userId.longValue()) {
            order.setBuyConfirmStatus(CommEnum.TRUE.getCode());
        } else {
            return BaseResponse.error("登陆用户没有对应的订单");
        }
        orderManager.updateIgnoreNull(order);
        //如果双方均确认完成交易 买方获得pig币
        if (CommEnum.TRUE.getCode().intValue() == order.getSellConfirmStatus().intValue()) {
            Users users = usersService.getById(order.getPurchaseUserId());
            users.setPigCurrency(users.getPigCurrency() + 1);
            usersService.updateById(users);

            //取消订单超时任务
            RedisUtils.remove("task:order-" + order.getPigOrderSn());
        }
        return BaseResponse.success();
    }
}