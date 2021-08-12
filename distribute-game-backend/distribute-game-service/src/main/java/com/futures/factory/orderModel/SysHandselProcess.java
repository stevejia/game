package com.futures.factory.orderModel;

import com.alibaba.fastjson.JSON;
import com.futures.common.enums.*;
import com.futures.common.utils.BigDecimalUtil;
import com.futures.common.utils.DateUtil;
import com.futures.common.utils.ThreadLocalUtil;
import com.futures.factory.OrderModelServer;
import com.futures.manager.PigOrderManager;
import com.futures.manager.UserExclusivePigManager;
import com.futures.model.dto.FreePigGoodsReqDto;
import com.futures.model.entity.PigGoods;
import com.futures.model.entity.PigOrder;
import com.futures.model.entity.SysUser;
import com.futures.model.entity.UserExclusivePig;
import com.futures.service.PigGoodsService;
import com.futures.service.SysUserService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.util.MD5;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统赠送
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 17:21
 */
@Slf4j
@Service
public class SysHandselProcess implements OrderModelServer<FreePigGoodsReqDto>{

    @Autowired
    private PigGoodsService pigGoodsService;
    @Autowired
    private UserExclusivePigManager exclusivePigManager;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private SysUserService userService;

    @Override
    public boolean isSupport(OrderTypeEnum orderTypeEnum) {
        return OrderTypeEnum.SYS_HANDSEL.getCode() == orderTypeEnum.getCode();
    }

    @Override
    public void verif(FreePigGoodsReqDto param) {
        Long loginUserId = WebUtils.getCurrentUserId();
        SysUser user = userService.getById(loginUserId);
        log.info("操作赠送系统登录用户 user:{}",JSON.toJSONString(user));
        if(null == user){
            throw new BizException("当前登录用户不存在");
        }
        if(!StringUtils.equals(MD5.getMD5Code(param.getTwoPassword()),user.getTwoLevelPassword())){
            log.warn("系统赠送 SysHandselModel verif 二级密码错误... password:{}",param.getTwoPassword());
            throw new BizException("二级密码错误");
        }
    }

    @Override
    public void createPig(FreePigGoodsReqDto param) {
        log.info("SysHandselModel createPig param:{}", JSON.toJSONString(param));
        List<PigGoods> goodsList = pigGoodsService.list();
        PigGoods goods = goodsList.stream()
                .filter(item -> BigDecimalUtil.compare(item.getSmallPrice(),item.getLargePrice(), param.getGoodPrice()))
                .findFirst()
                .orElse(null);
        Assert.notNull(goods,"赠送价格不在现有木材区间");
        List<UserExclusivePig> pigs = convertPig(param, goods);
        exclusivePigManager.insertForeach(pigs);
        ThreadLocalUtil.set("pig",pigs);
    }


    @Override
    public BaseResponse createOrder(FreePigGoodsReqDto param) {
        log.info("SysHandselModel createOrder param:{}", JSON.toJSONString(param));
        List<UserExclusivePig> pigs = ThreadLocalUtil.get("pig");
        for(UserExclusivePig pig : pigs){
            PigOrder order = orderManager.convert(param.getUserId(),null,PayStatusEnum.SUCCESS,OrderTypeEnum.SYS_HANDSEL, pig);
            order.setSellConfirmStatus(CommEnum.TRUE.getCode());
            order.setBuyConfirmStatus(CommEnum.TRUE.getCode());
            orderManager.insert(order);
            order = orderManager.getByOrderNo(order.getPigOrderSn());
            //为木材实例绑定订单ID
            pig.setOrderId(order.getOrderId());
        }

        exclusivePigManager.updateForeach(pigs);
        ThreadLocalUtil.remove("pig");
        return BaseResponse.success();
    }

    public List<UserExclusivePig> convertPig(FreePigGoodsReqDto param,PigGoods goods){
        List<UserExclusivePig> pigs =new ArrayList();
        for(int i = 0;i < param.getNumber();i++){
            UserExclusivePig pig = new UserExclusivePig();
            pig.setUserId(param.getUserId());
            pig.setOrderId(null);
            pig.setPigId(goods.getId());
            pig.setIsAbleSale(SaleStatusEnum.TRUE.getCode());
            pig.setPrice(param.getGoodPrice());
            pig.setFromUserId(null);
            pig.setAppointUserId(null);
            pig.setBuyTime(DateUtil.getNowDate());
//            pig.setEndTime(pig.getBuyTime() + DateUtil.getLongTime(goods.getContractDays()));
            pig.setEndTime(DateUtil.getNowDate());
            pig.setPigSalt("1");
            pig.setBuyType(BuyTypeEnum.SYS_HANDSEL.getCode());
            pig.setNowContractDays(goods.getContractDays());
            pig.setNowIncomeRatio(goods.getIncomeRatio());
            pig.setIsPigLock(LockStatusEnum.NOT_LOCK.getCode());
            pigs.add(pig);
        }
        return pigs;
    }


}
