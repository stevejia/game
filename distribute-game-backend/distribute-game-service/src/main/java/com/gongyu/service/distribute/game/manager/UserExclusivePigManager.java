package com.gongyu.service.distribute.game.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.PigGoodsMapper;
import com.gongyu.service.distribute.game.mapper.UserExclusivePigMapper;
import com.gongyu.service.distribute.game.model.dto.PigPageReqDto;
import com.gongyu.service.distribute.game.model.dto.PrizeTodayReqDto;
import com.gongyu.service.distribute.game.model.dto.TransfReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDTO;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.ConfigService;
import com.gongyu.service.distribute.game.service.PigGoodsService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 17:51
 */
@Service
public class UserExclusivePigManager {

    @Autowired
    private UserExclusivePigMapper pigMapper;
    @Autowired
    private UserExclusivePigDelManager pigDelManager;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private SellRecordManager sellRecordManager;
    @Autowired
    private UserExclusivePigManager pigManager;
    @Autowired
    private PigGoodsMapper goodsMapper;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private ConfigService configService;

    public UserExclusivePig findById(Long pigId){
        return pigMapper.selectById(pigId);
    }

    public UserExclusivePig findByOrderId(Long orderId){
        return pigMapper.findByOrderId(orderId);
    }

    public List<UserExclusivePigDTO> findPage(IPage<UserExclusivePigDTO> page, PigPageReqDto pageParam){
        return pigMapper.findPage(page,pageParam);
    }

    public List<UserExclusivePig> findPageByUser(IPage page, Long userId, SaleStatusEnum saleStatusEnum,TransfReqDto param){
        return pigMapper.findPageByUser(page, userId,saleStatusEnum.getCode(), param);
    }

    public List<UserExclusivePigDTO> prizeToday(IPage<UserExclusivePigDTO> page, PrizeTodayReqDto param){
        return pigMapper.prizeToday(page,param);
    }
    
    public List<UserExclusivePigDTO> prizeAllToday(PrizeTodayReqDto param){
        return pigMapper.prizeAllToday(param);
    }

    public UserExclusivePig convert(Long userId, Long pigId, SaleStatusEnum saleStatusEnum, BigDecimal price
                                        , Long fromUserId, Long appoinUserId, Long buyTime, Long endTime, String buyType
                                        , Integer nowContractDays, BigDecimal nowIncomeRatio, LockStatusEnum pigLockEnum){
        UserExclusivePig pig = new UserExclusivePig();
        pig.setUserId(userId);
        pig.setOrderId(null);
        pig.setPigId(pigId);
        pig.setIsAbleSale(saleStatusEnum.getCode());
        pig.setPrice(price);
        pig.setFromUserId(fromUserId);
        pig.setAppointUserId(appoinUserId);
        pig.setBuyTime(buyTime);
        pig.setEndTime(endTime);
        pig.setPigSalt("1");
        pig.setBuyType(buyType);
        pig.setNowContractDays(nowContractDays);
        pig.setNowIncomeRatio(nowIncomeRatio);
        pig.setIsPigLock(pigLockEnum.getCode());
        return pig;
    }

    public void insert(UserExclusivePig pig){
        pigMapper.insert(pig);
    }

    public void insertForeach(List<UserExclusivePig> list){
        pigMapper.insertForeach(list);
    }

    public void update(UserExclusivePig pig){
        pigMapper.updateById(pig);
    }

    public void updateSale(UserExclusivePig pig,SaleStatusEnum saleStatusEnum){
        pig.setIsAbleSale(saleStatusEnum.getCode());
        pigManager.update(pig);
    }

    public void updateForeach(List<UserExclusivePig> list){
        pigMapper.updateForeach(list);
    }

    public List<UserExclusivePig> findListByUser(){
        return pigMapper.findList();
    }

    /**
     * 精灵分裂
     */
    public List<UserExclusivePig> splitPig(UserExclusivePig pig,PigGoods maxPriceGoods){
        List<UserExclusivePig> splitPigs = new ArrayList<>();
        Config config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "good_fission_number"));
//        Integer splitNum = maxPriceGoods.getSplitNum();
        UserExclusivePigDel pigDel = pigDelManager.convert(pig);
        Long insertId = pigDelManager.insert(pigDel);

        BigDecimal pigPrice = pig.getPrice().divide(new BigDecimal(config.getConfigValue()), 2, RoundingMode.HALF_UP);
        List<PigGoods> list = goodsService.list();
        PigGoods goods = list.stream()
                .filter(item -> item.getSmallPrice().compareTo(pigPrice) < 1 && item.getLargePrice().compareTo(pigPrice) > 0)
                .findFirst()
                .orElseThrow(() -> new BizException("没有找到对应精灵"));
        //根据价格查看在哪个精灵区间
        for(int i = 0; i < Integer.valueOf(config.getConfigValue()); i ++){
            Date endDate = DateUtil.addDate(DateUtil.getDate(DateUtil.getNowDate()), goods.getContractDays());
            UserExclusivePig exclusivePig = this.convert(pig.getUserId(), goods.getId(), SaleStatusEnum.FALSE, pigPrice, 0L, 0L, DateUtil.getNowDate(), DateUtil.getDate(endDate), BuyTypeEnum.PIG_SPLIT.getDesc(), pig.getNowContractDays(), goods.getIncomeRatio(), LockStatusEnum.NOT_LOCK);
            exclusivePig.setPigDelId(insertId);
            this.insert(exclusivePig);

            // TODO: 2020/6/30 如若允许出售收益价格为分裂价格 ，此处当添加完毕精灵后需要创建一笔已完成订单。
            splitPigs.add(exclusivePig);
        }
        pig.setIsPigLock(LockStatusEnum.LOCK.getCode());
        this.update(pig);
        return splitPigs;
    }

    /**
     * 将出售的推广收入转化为精灵并添加到开奖精灵中
     * @param exclusivePigs
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<UserExclusivePig> addSellIncomePig(List<UserExclusivePig> exclusivePigs){
        List<UserExclusivePig> pigs = new LinkedList<>();
        PigGoods maxPriceGoods = goodsMapper.findMaxPrice();
        List<SellRecord> list = sellRecordManager.findList(CommEnum.FALSE);
        list.stream().forEach(e -> {
            List<UserExclusivePig> splitPigs = null;
            //生成出售收益的订单与对应的pig
            PigGoods goods = goodsService.getById(e.getPigLevel());
            UserExclusivePig pig = pigManager.convert(e.getUserId(), e.getPigLevel(), SaleStatusEnum.TRUE, e.getSellIncom(), null, null, DateUtil.getNowDate(), DateUtil.getNowDate() + DateUtil.getLongTime(goods.getContractDays()), "", goods.getContractDays(), goods.getIncomeRatio(), LockStatusEnum.NOT_LOCK);
            pig.setBuyType(BuyTypeEnum.SELL_INCOM.getCode());
            pigMapper.insert(pig);
            PigOrder order = orderManager.convert(e.getUserId(), e.getUserId(), PayStatusEnum.SUCCESS, OrderTypeEnum.SELL_REVENUE, pig);
            order.setSellConfirmStatus(CommEnum.TRUE.getCode());
            order.setBuyConfirmStatus(CommEnum.TRUE.getCode());
            PigOrder insertOrder = orderManager.insert(order);

            pig.setOrderId(insertOrder.getOrderId());
            pigMapper.updateById(pig);

            //精灵是否分裂 暂不允许出售价值超过最大精灵的价值，防止出售时进行分裂无法绑定订单号
            if(pig.getPrice().compareTo(maxPriceGoods.getLargePrice()) > 0){
                splitPigs = ((UserExclusivePigManager)AopContext.currentProxy()).splitPig(pig, maxPriceGoods);
            }
            if(CollectionUtils.isEmpty(splitPigs)){
                pigs.add(pig);
            }else{
                pigs.addAll(splitPigs);
            }
            e.setStatus(CommEnum.TRUE.getCode());
            sellRecordManager.updateIgnoreNull(e);
        });
        exclusivePigs.addAll(pigs);
        return pigs;
    }
}
