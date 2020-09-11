package com.gongyu.application.distribute.game.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.common.enums.OrderTypeEnum;
import com.gongyu.service.distribute.game.common.enums.PayStatusEnum;
import com.gongyu.service.distribute.game.common.enums.PigStatusEnum;
import com.gongyu.service.distribute.game.manager.AlPersonAuthManager;
import com.gongyu.service.distribute.game.mapper.PigReservationMapper;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.PigAppeal;
import com.gongyu.service.distribute.game.model.entity.PigGoods;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
import com.gongyu.service.distribute.game.model.entity.PigReservation;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.service.distribute.game.service.impl.PigReservationServiceImpl;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.CollectionUtils;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/9 20:46

*/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class TestController{

    @Autowired
    private PigReservationServiceImpl service;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private PigReservationMapper reservationMapper;

    @Autowired
    private PigOrderService orderService;
    @Autowired
    private OrderService ordersService;
    Map<String,Long> map = new HashMap<>();

    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MyService myService;
    @Autowired
    private PigAppealService appealService;
    @Autowired
    private PigAwardLogService pigAwardLogService;
    @Autowired
    private AlPersonAuthManager alPersonAuthManager;
    @Autowired
    private AccountLogService accountLogService;

    @Test
    public void test1(){
        WebUtils.setCurrentUserId(2082L);
        FreePigGoodsReqDto param = new FreePigGoodsReqDto();
        String s = "{\"goodPrice\":200,\"number\":2,\"twoPassword\":\"123456\",\"userId\":2053}";
        param = JSON.parseObject(s,param.getClass());
        ordersService.createOrder(OrderTypeEnum.ACTIVITY,param);
//        usersService.checkUpgrade(2043L);
//        AuthRechargeDto param = new AuthRechargeDto();
//        param.setId(144L);
//        param.setStatus(1);
//        rechargeService.auth(param);
    }

    @Test
    public void test (){
        Long loginId = WebUtils.getCurrentUserId();
        HomeResultDto dto = new HomeResultDto();
        List<PigGoods> goods = goodsService.list();
        List<PigGoodsResultDto> resultDtos = new ArrayList<>();
        goods.forEach(e -> {
            PigGoodsResultDto result = new PigGoodsResultDto();
            BeanUtils.copyProperties(e,result);

            String format = DateUtils.format(new Date(), DateUtils.DEFAULT_TIME_FORMAT);
            Date nowDate = DateUtils.parse(format, DateUtils.DEFAULT_TIME_FORMAT);
            String format1 = DateUtils.format(e.getEndTime(), DateUtils.DEFAULT_TIME_FORMAT);
            String format2 = DateUtils.format(e.getStartTime(), DateUtils.DEFAULT_TIME_FORMAT);
            result.setStartTime(DateUtils.parse(DateUtils.format(new Date(),DateUtils.DEFAULT_DATE_FORMAT + StringUtils.SPACE + format2),DateUtils.DEFAULT_DATE_TIME_FORMAT));
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
                }else if(!CollectionUtils.isEmpty(reservations)){
                    result.setPigStatus(PigStatusEnum.RESERVAED.getCode());
                }else if(nowDate.before(DateUtils.parse(format2, DateUtils.DEFAULT_TIME_FORMAT))){
                    result.setPigStatus(PigStatusEnum.RESERVA.getCode());
                }else{
                    result.setPigStatus(PigStatusEnum.PRACTICE.getCode());
                }
            }
            resultDtos.add(result);
        });
        System.out.println(JSON.toJSONString(resultDtos));
        compareTo(resultDtos);
        System.out.println(JSON.toJSONString(map));
        Collections.sort(resultDtos);
        System.out.println(JSON.toJSONString(resultDtos));
    }

    public void compareTo(List<PigGoodsResultDto> resultDtos){

        List<Map<String,Long>> list = new ArrayList<>();

        Date nowDate = DateUtils.currentDate();
        for(PigGoodsResultDto resultDto : resultDtos){

            System.out.println(DateUtils.format(resultDto.getStartTime(),DateUtils.DEFAULT_DATE_TIME_FORMAT));
            long l = resultDto.getStartTime().getTime() - nowDate.getTime();
            if(l < 0){
                l *= -1;
                l += 100000000L;
            }
            resultDto.setOpenms(l);
            this.map.put(resultDto.getId().toString(),l);
        }
    }

    @Test
    public void appeal(){
//        LambdaQueryWrapper<PigAppeal> eq = Wrappers.<PigAppeal>lambdaQuery();
//        eq.orderByDesc(PigAppeal::getId);
        // 申诉人手机号
//        IPage<PigAppeal> page = appealService.page(new Page<>(0, 10),eq);
//        usersService.modifyAccount(2080, 2, new BigDecimal(123), 1, "", IncomeTypeEnum.PROMOTE);

        usersService.modifyAccount(2100, 1, new BigDecimal(100), 2, "", IncomeTypeEnum.PROMOTE);
        WebUtils.setCurrentUserId(2080L);
        IPage<AccountStoreDto> accountStoreDtoIPage = accountLogService.queryStore(new Page<>(0, 10));
//        myService.accumIncome(new Page<>(0, 10), 2080L, 2);
    }

    @Test
    public void orderLuck(){
        rechargeService.queryRecharge(new Page<>(0, 10), new RechargeDto());
//        IPage<PigAwardLogPageDto> pigAwardLogPageDtoIPage = pigAwardLogService.queryPigAwardLog(new Page<>(0, 10), new PigAwardLogPageDto());
    }

    @Test
    public void checkPeople() throws ServiceException, Exception {
        WebUtils.setCurrentUserId(1L);
        myService.auth(new AuthReqDto());

//        BaseResponse response = myService.auth(param);
    }

    @Test
    public void aw(){
        IPage<PigAwardLogPageDto> awardLog = pigAwardLogService.queryPigAwardLog(new Page<>(0, 10), new PigAwardLogPageDto());
        System.out.println(JSON.toJSONString(awardLog));
    }

}
