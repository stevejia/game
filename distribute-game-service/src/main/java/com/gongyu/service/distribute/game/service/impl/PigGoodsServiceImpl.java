package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.manager.UserExclusivePigManager;
import com.gongyu.service.distribute.game.mapper.PigGoodsMapper;
import com.gongyu.service.distribute.game.mapper.PigReservationMapper;
import com.gongyu.service.distribute.game.model.dto.HomeResultDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsResultDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSaveDto;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PigGoodsServiceImpl extends CrudServiceSupport<PigGoodsMapper, PigGoods> implements PigGoodsService  {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserExclusivePigService exclusivePigService;
    @Autowired
    private PigReservationService reservationService;

    @Autowired
    private PigReservationMapper reservationMapper;
    @Autowired
    private PigOrderService orderService;

    @Override
    public IPage<PigGoods> queryPigGoods(IPage<PigGoods> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePigGoods(PigGoodsSaveDto pigGoodsSaveDto) {
        PigGoods pigGoods = new PigGoods();
        BeanUtils.copyProperties(pigGoodsSaveDto, pigGoods);
        cleanCache();
        this.save(pigGoods);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPigGoods(PigGoodsModifyDto pigGoodsModifyDto) {
        PigGoods pigGoods = new PigGoods();
        BeanUtils.copyProperties(pigGoodsModifyDto, pigGoods);
        cleanCache();
        this.updateById(pigGoods);
    }

    public BaseResponse home(){
        Long loginId = WebUtils.getCurrentUserId();
        HomeResultDto dto = new HomeResultDto();
        List<PigGoods> goods = RedisUtils.get("home_goods");
        if(goods == null){
            goods = this.list();
        }
        List<PigGoodsResultDto> resultDtos = new ArrayList<>();
        goods.forEach(e -> {
            PigGoodsResultDto result = new PigGoodsResultDto();
            BeanUtils.copyProperties(e,result);
            String format = DateUtils.format(new Date(), DateUtils.DEFAULT_TIME_FORMAT);
            Date nowDate = DateUtils.parse(format, DateUtils.DEFAULT_TIME_FORMAT);
            String format1 = DateUtils.format(e.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT);
            String format2 = DateUtils.format(e.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT);
            Date format2Date = DateUtils.parse(DateUtils.format(new Date(), DateUtils.DEFAULT_DATE_FORMAT + StringUtils.SPACE + format2), DateUtils.DEFAULT_DATE_TIME_FORMAT);
            result.setStartTime(format2Date);
            result.setEndTime(DateUtils.parse(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT + StringUtils.SPACE + format1),DateUtils.DEFAULT_DATE_TIME_FORMAT));

            if(0 == e.getIsDisplay().intValue()){
                result.setPigStatus(PigStatusEnum.NOT_PUBLIC.getCode());
            }else{
                List<PigReservation> reservations = reservationMapper.findReservated(loginId,e.getId());
                PigOrder orders = orderService.getOne(new QueryWrapper<PigOrder>().eq("pig_level", e.getId()).eq("purchase_user_id", loginId).eq("pay_status", PayStatusEnum.PROCESSING.getCode()));

                if(nowDate.before(DateUtils.parse(format1,DateUtils.DEFAULT_TIME_FORMAT)) && nowDate.after(DateUtils.parse(format2,DateUtils.DEFAULT_TIME_FORMAT))){
                    result.setPigStatus(PigStatusEnum.OPEN_UP.getCode());
                }else if(null != orders && PayStatusEnum.PROCESSING.getCode() == orders.getPayStatus()){
                    result.setPigStatus(PigStatusEnum.WAIT_CONTRACT.getCode());
                    result.setOrderNo(orders.getPigOrderSn());
                }else if(!CollectionUtils.isEmpty(reservations) && new Date().before(format2Date)){
                    result.setPigStatus(PigStatusEnum.RESERVAED.getCode());
                }else if(nowDate.before(DateUtils.parse(format2, DateUtils.DEFAULT_TIME_FORMAT))){
                    result.setPigStatus(PigStatusEnum.RESERVA.getCode());
                }else{
                    result.setPigStatus(PigStatusEnum.PRACTICE.getCode());
                }
            }
            resultDtos.add(result);
        });
        List<Article> articles = RedisUtils.get("home_article");
        //首页公告 5 = 首页公告
        if(CollectionUtils.isEmpty(articles)){
            articles = articleService.list(new QueryWrapper<Article>().eq("status",CommEnum.FALSE.getCode()).eq("cate_id",2));
        }
        this.compareToStartTime(resultDtos);
//        Collections.sort(resultDtos); 排序问题给前端做
        dto.setGoods(resultDtos);
        dto.setArticles(articles);
        return BaseResponse.success(dto);
    }

    public void compareToStartTime(List<PigGoodsResultDto> resultDtos){
        Date nowDate = DateUtils.currentDate();
        for(PigGoodsResultDto resultDto : resultDtos){
            if(CommEnum.FALSE.getCode() == resultDto.getIsDisplay()){
                resultDto.setOpenms(200000000L);
            }else{
                long l = resultDto.getEndTime().getTime() - nowDate.getTime();
                if(l < 0){
                    l += 100000000L;
                }
                resultDto.setOpenms(l);
            }
        }
    }

    public void cleanCache(){
        Object home = RedisUtils.get("goods");
        if(null != home){
            RedisUtils.remove("goods");
        }
    }
}