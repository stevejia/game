package com.futures.factory.orderModel;

import com.alibaba.fastjson.JSON;
import com.futures.common.enums.CommEnum;
import com.futures.common.enums.LockStatusEnum;
import com.futures.common.enums.OrderTypeEnum;
import com.futures.common.enums.PayStatusEnum;
import com.futures.common.enums.SaleStatusEnum;
import com.futures.common.utils.DateUtil;
import com.futures.common.utils.ThreadLocalUtil;
import com.futures.factory.OrderModelServer;
import com.futures.manager.PigOrderManager;
import com.futures.model.dto.PayOrderReqDto;
import com.futures.model.entity.PigGoods;
import com.futures.model.entity.PigOrder;
import com.futures.model.entity.UserExclusivePig;
import com.futures.model.entity.Users;
import com.futures.service.*;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 下单交易处理器
 *
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/12 15:16
 */
@Slf4j
@Service
public class PayOrderProcess implements OrderModelServer<PayOrderReqDto> {


    @Autowired
    private PigOrderService orderService;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserExclusivePigService pigService;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private SysSmsFpi sysSmsFpi;

    @Override
    public boolean isSupport(OrderTypeEnum orderTypeEnum) {
        return OrderTypeEnum.NORMAL_POSSESSION.equals(orderTypeEnum);
    }

    @Override
    public void verif(PayOrderReqDto param) {
        log.info("下单交易 PayOrderProcess verif 订单进入校验阶段，param:{}", JSON.toJSONString(param));
        PigOrder order = orderManager.getByOrderNo(param.getOrderNo());
        if (PayStatusEnum.FREEZE.getCode() == order.getPayStatus()) {
            log.warn("下单交易 PayOrderProcess verif 交易失败，订单号：" + param.getOrderNo() + " 已冻结");
            throw new BizException("交易失败，订单号：" + param.getOrderNo() + " 已冻结");
        } else if (PayStatusEnum.SUCCESS.getCode() == order.getPayStatus()) {
            log.warn("下单交易 PayOrderProcess verif 交易失败，订单号：" + param.getOrderNo() + " 已支付");
            throw new BizException("交易失败，订单号：" + param.getOrderNo() + " 不能重复支付");
        } else if (PayStatusEnum.TRAN_TIMEOUT.getCode() == order.getPayStatus()) {
            log.warn("下单交易 PayOrderProcess verif 交易失败，订单号：" + param.getOrderNo() + " 已超时");
            throw new BizException("交易失败，订单号：" + param.getOrderNo() + " 已超时");
        }
        Users user = usersService.getById(order.getPurchaseUserId());
        if (LockStatusEnum.LOCK.getCode() == user.getIsLock()) {
            log.warn("下单交易 PayOrderProcess verif 交易失败，订单号：" + param.getOrderNo() + " 用户已经被锁定");
            throw new BizException("交易失败，用户:" + user.getNickname() + " 被锁定");
        }
        if (StringUtils.isBlank(user.getPaypwd())) {
            log.warn("下单交易 PayOrderProcess verif 交易失败，订单号：" + param.getOrderNo() + " 用户未设置支付密码");
            throw new BizException("交易失败，您还没有设置支付密码");
        }
        if (!StringUtils.equals(MD5.getMD5Code(param.getPayPwd()), user.getPaypwd())) {
            throw new BizException("支付密码错误!");
        }
        ThreadLocalUtil.set("order", order);
    }

    @Override
    public void createPig(PayOrderReqDto param) {
        PigOrder order = ThreadLocalUtil.get("order");
        UserExclusivePig oldPig = pigService.getById(order.getPigId());
        UserExclusivePig newPig = new UserExclusivePig();
        BeanUtils.copyProperties(oldPig, newPig);
        resetPig(newPig, order);
        oldPig.setIsPigLock(LockStatusEnum.LOCK.getCode());
        pigService.updateById(oldPig);
        pigService.save(newPig);
        order.setPigId(newPig.getId());
    }

    @Override
    public BaseResponse createOrder(PayOrderReqDto param) {
        //更新订单绑定用户信息
        PigOrder order = ThreadLocalUtil.get("order");
        order.setImgUrl(param.getPayCertUrl());
        order.setEndTime(DateUtil.getNowDate());
        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
    	//用户付款后 更新订单的状态 默认用户确认付款
        order.setBuyConfirmStatus(CommEnum.TRUE.getCode());
        orderService.updateById(order);
        //accountLogService.convertAndInsert(order.getUserId(), new BigDecimal(String.valueOf(order.getPigPrice())).negate(), new BigDecimal("0"), 0, new BigDecimal("0"), IncomeTypeEnum.PAY.getDesc(), IncomeTypeEnum.PAY, order.getPigId(), "", null);
        ThreadLocalUtil.remove("order");
        //新系统不需要短信提醒功能
//        Users byId = usersService.getById(order.getSellUserId());
        //  发送短信
//        if (byId != null) {
//            sysSmsFpi.sendSms(byId.getMobile(), "【航海世界】尊敬的用户，您的订单有新动态，请尽快审核");
//        }
        return BaseResponse.success();
    }

    public void resetPig(UserExclusivePig pig, PigOrder order) {
        PigGoods goods = goodsService.getById(order.getPigLevel());
        pig.setId(null);
        pig.setOrderId(order.getOrderId());
        pig.setUserId(order.getPurchaseUserId());
        pig.setFromUserId(order.getSellUserId());
        pig.setIsAbleSale(SaleStatusEnum.FALSE.getCode());
        Date now = new Date();
        Date endTime = DateUtil.addDate(now, goods.getContractDays());
        pig.setBuyTime(DateUtil.getDate(now));
        pig.setEndTime(DateUtil.getDate(endTime));
        pig.setBuyType(String.valueOf(OrderTypeEnum.NORMAL_POSSESSION.getCode()));
        pig.setPigSalt(UUID.randomUUID().toString());
        pig.setAppointUserId(null);
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal("1").negate());
    }
}
