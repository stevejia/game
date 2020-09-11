package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.manager.DelayQueueManager;
import com.gongyu.service.distribute.game.manager.PersonAuthRecordManager;
import com.gongyu.service.distribute.game.manager.PigAwardLogManager;
import com.gongyu.service.distribute.game.manager.RevealLuckyManager;
import com.gongyu.service.distribute.game.mapper.PigReservationMapper;
import com.gongyu.service.distribute.game.model.DelayTask;
import com.gongyu.service.distribute.game.model.dto.PigReservationModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigReservationSaveDto;
import com.gongyu.service.distribute.game.model.dto.ReservatDto;
import com.gongyu.service.distribute.game.model.dto.RobProductsDto;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class PigReservationServiceImpl extends CrudServiceSupport<PigReservationMapper, PigReservation> implements PigReservationService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PigReservationMapper reservationMapper;
    @Autowired
    private PigReservationService reservationService;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private PigAwardLogService awardLogService;
    @Autowired
    private PigAwardLogManager awardLogManager;
    @Autowired
    private DelayQueueManager delayQueueManager;
    @Autowired
    private RevealLuckyManager luckyManager;
    @Autowired
    private Redisson redisson;
    @Autowired
    private UserPaymentService paymentService;
    @Autowired
    private PersonAuthRecordManager authRecordManager;

    @Override
    public IPage<PigReservationModifyDto> queryPigReservation(IPage<PigReservationModifyDto> page, PigReservationModifyDto dto) {
        List<PigReservationModifyDto> list = reservationMapper.findPage(page, dto);
        return page.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigReservation(PigReservationSaveDto pigReservationSaveDto) {
        PigReservation pigReservation = new PigReservation();
        BeanUtils.copyProperties(pigReservationSaveDto, pigReservation);
        pigReservation.setReservationTime(DateUtil.getNowDate());
        this.save(pigReservation);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigReservation(PigReservationModifyDto pigReservationModifyDto) {
        PigReservation pigReservation = new PigReservation();
        BeanUtils.copyProperties(pigReservationModifyDto, pigReservation);
        this.updateById(pigReservation);
    }

    @Transactional
    @Override
    public BaseResponse reservat(ReservatDto param) {
        Long awardId = null;
        PigGoods goods = goodsService.getById(param.getGoodsId());
        Assert.notNull(goods, "没有找到对应精灵");
        Date nowDate = DateUtils.parse(DateUtils.format(DateUtils.currentDate(), DateUtils.DEFAULT_TIME_FORMAT), DateUtils.DEFAULT_TIME_FORMAT);
        Date resDate = DateUtils.parse(DateUtils.format(goods.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT), DateUtils.DEFAULT_TIME_FORMAT);
        if (resDate.before(nowDate)) {
            return BaseResponse.error("不在预约时间");
        }
        //检查是否拥有正确的收款方式
        if (!paymentService.checkProperPayment()) {
            return BaseResponse.error("你还没有正确的收款方式");
        }
        //检查是否做过实名认证
        PersonAuthRecord authRecord = authRecordManager.findByUserId(WebUtils.getCurrentUserId());
        if (null == authRecord) {
            return BaseResponse.error("还没做实名认证");
        }

        boolean b = usersService.modifyPayPoints(Math.toIntExact(param.getUserId()), goods.getReservation(), 2, "预约精灵", IncomeTypeEnum.RESERVAT);
        if (!b) {
            return BaseResponse.error("积分不足");
        }
        //找到该精灵的下一场次
        PigAwardLog awardLog = awardLogService.getOne(new QueryWrapper<PigAwardLog>().eq("open_result", CommEnum.FALSE.getCode()).eq("pig_id", param.getGoodsId()));
        if (null == awardLog) {
            //初始化中奖记录
            awardLog = awardLogManager.convert(param.getGoodsId(), OpenResultEnum.NOT_OPEN);
            PigAwardLog insert = awardLogManager.insert(awardLog);
            awardId = insert.getId();
        } else {
            awardId = awardLog.getId();
        }

        //如果当前没有开奖延时任务，则添加一个开奖任务
        this.addOpenLuckQueue(goods, param.getGoodsId());

        PigReservation reservat = reservationMapper.findByUserAndPig(param.getUserId(), param.getGoodsId(), awardId);
        Assert.isNull(reservat, "已经预约过了");
        //预约记录表内插入预约信息
        Assert.notNull(awardId, "错误");
        PigReservation convertDb = convert(param.getUserId(), awardId, goods);
        reservationMapper.insert(convertDb);
        return BaseResponse.success("预约成功");
    }

    @Override
    public BaseResponse robProducts(RobProductsDto param) {
        Set<Long> users;
        PigGoods goods = goodsService.getById(param.getPigId());
        Assert.notNull(goods, "没有找到对应精灵");
        Date nowDate = DateUtils.parse(DateUtils.format(DateUtils.currentDate(), DateUtils.DEFAULT_TIME_FORMAT), DateUtils.DEFAULT_TIME_FORMAT);
        Date resDate = DateUtils.parse(DateUtils.format(goods.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT), DateUtils.DEFAULT_TIME_FORMAT);
        if (resDate.before(nowDate)) {
            return BaseResponse.error("不在抢占时间");
        }
        users = RedisUtils.get("robProduct:" + param.getPigId());
        if (!CollectionUtils.isEmpty(users) && users.contains(param.getUserId())) {
            return BaseResponse.success("正在抢占中");
        } else if (null == users) {
            users = new HashSet<>();
        }

        //检查是否拥有正确的收款方式
        if (!paymentService.checkProperPayment()) {
            return BaseResponse.error("你还没有正确的收款方式");
        }
        //检查是否做过实名认证
        PersonAuthRecord authRecord = authRecordManager.findByUserId(WebUtils.getCurrentUserId());
        if (null == authRecord) {
            return BaseResponse.error("还没做实名认证");
        }
        Users user = usersService.getById(param.getUserId());
        PigReservation reservation = reservationService.getOne(new QueryWrapper<PigReservation>().eq("user_id", user.getId()).eq("pig_id", goods.getId()).eq("is_click_buy", IsClickBuyEnum.FALSE.getCode()));
        if (null == reservation && null != user && user.getPayPoints() < goods.getAdoptiveEnergy()) {
            return BaseResponse.error("积分不足");
        }
        if (CollectionUtils.isEmpty(users)) {
            this.addOpenLuckQueue(goods, param.getPigId());
        }
        users.add(param.getUserId());
        //加入Redis缓存
        if (null == RedisUtils.get("task:" + param.getPigId())) {
            log.warn("抢购失败编号：" + param.getPigId() + "开奖任务不存在");
            RedisUtils.remove("robProduct:" + param.getPigId());
            return BaseResponse.error("抢购失败" + goods.getGoodsName() + "已开奖或没有开奖计划");
        }
        RedisUtils.set("robProduct:" + param.getPigId(), users);
        //扣除积分
        if (null == reservation) {
            usersService.modifyPayPoints(Math.toIntExact(param.getUserId()), goods.getAdoptiveEnergy(), 2, "抢购精灵", IncomeTypeEnum.PANIC_BUY);
        } else {
            reservation.setIsClickBuy(IsClickBuyEnum.TRUE.getCode());
            reservationService.updateById(reservation);
        }
        return BaseResponse.success();
    }


    /**
     * 添加开奖延时任务
     *
     * @param goods
     * @param pigId
     */
    public void addOpenLuckQueue(PigGoods goods, Long pigId) {
        if (null != RedisUtils.get("task:" + pigId)) {
            return;
        }
        RLock lock = redisson.getLock("delay_task_lock:" + pigId);
        //计算延时执行时间
        long exectTime = exectTime(goods);
        log.info("加入开奖逻辑 goods:{},exetime:{}", goods.getId(), exectTime);
        //第一个人抢占资源，同时添加延时执行开奖计划
        try {
            lock.lock();
            if (null == RedisUtils.get("task:" + pigId)) {
                DelayTask task = luckyManager.convertTaskBase(goods, DelayTaskEnum.REVEAL_LUCKY, exectTime);
                delayQueueManager.put(task);
                //防止宕机重启后延时任务消失，将任务加入到缓存
                RedisUtils.set("task:" + pigId, task, exectTime);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 计算开奖执行时间
     *
     * @param goods
     * @return
     */
    public long exectTime(PigGoods goods) {
        long time = 0;
        String exetime;
        //一天开奖一次
//        if(CommEnum.FALSE.getCode() == goods.getGameOpenType().intValue()){
        String nowDate = DateUtils.format(new Date(), DateUtils.DEFAULT_DATE_FORMAT);
        exetime = nowDate + StringUtils.SPACE + DateUtils.format(goods.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT);
        log.info("找到精灵开奖时间 一 goods:{}，exetTime:{}", goods.getId(), exetime);
        Date date = DateUtils.parse(exetime, DateUtils.DEFAULT_DATE_TIME_FORMAT);
        if (date.before(new Date())) {
            //把日期往后增加一天
            return -1;
        }
        time = date.getTime() - new Date().getTime();
//        }
        //一天开奖两次
//        if(CommEnum.TRUE.getCode() == goods.getGameOpenType().intValue()){
//            exetime = this.openDate(goods);
//            log.info("找到精灵开奖时间 二 goods:{}，exetTime:{}",goods.getId(),exetime);
//            Date date = DateUtils.parse(exetime, DateUtils.DEFAULT_DATE_TIME_FORMAT);
//            if(date.before(new Date())){
//                //把日期往后增加一天
//                return -1;
//            }
//            time = date.getTime() - new Date().getTime();
//        }
        return time;
    }

    /**
     * 找到一下次开奖时间
     *
     * @param goods
     * @return
     */
    public String openDate(PigGoods goods) {
        String nowDateSuffix = DateUtils.format(new Date(), DateUtils.DEFAULT_DATE_FORMAT);
        String fristDate = nowDateSuffix + StringUtils.SPACE + DateUtils.format(goods.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT);
        String secondDate = nowDateSuffix + StringUtils.SPACE + DateUtils.format(goods.getSecondEndTime(), DateUtils.DEFAULT_TIME_FORMAT);
        //找出最近的一次开奖时间

        if (DateUtil.before(fristDate)) {
            return fristDate;
        } else if (DateUtil.before(secondDate)) {
            return secondDate;
        } else {
            Date date = org.apache.commons.lang3.time.DateUtils.addDays(DateUtils.parse(fristDate, DateUtils.DEFAULT_DATE_TIME_FORMAT), 1);
            return DateUtils.format(date, DateUtils.DEFAULT_DATE_TIME_FORMAT);
        }
    }


    /**
     * 更新中奖状态
     *
     * @return
     */
    public List<PigReservation> luckStatus(Set<Long> users, List<Long> luckUsers, List<PigReservation> reservats) {
        for (PigReservation reservat : reservats) {
            if (users.contains(reservat.getId())) {
                reservat.setIsClickBuy(IsClickBuyEnum.TRUE.getCode());
                if (luckUsers.contains(reservat.getId())) {
                    reservat.setReservationStatus(CommEnum.TRUE.getCode());
                } else {
                    reservat.setReservationStatus(CommEnum.FALSE.getCode());
                }
            } else {
                reservat.setIsClickBuy(IsClickBuyEnum.FALSE.getCode());
            }
        }
        return reservats;
    }

    /**
     * 装配预约对象
     *
     * @param userId
     * @param awardId 场次ID
     * @param goods   精灵
     * @return
     */
    public PigReservation convert(Long userId, Long awardId, PigGoods goods) {
        PigReservation bean = new PigReservation();
        bean.setPigId(goods.getId());
        bean.setReservationTime(DateUtil.getNowDate());
        bean.setReservationStatus(CommEnum.FALSE.getCode());
        bean.setUserId(userId);
        bean.setPayPoints(goods.getReservation());
        bean.setReservationScene(awardId);
        bean.setIsClickBuy(IsClickBuyEnum.FALSE.getCode());
        return bean;
    }
}