package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.common.enums.LevelIncomEnum;
import com.gongyu.service.distribute.game.common.enums.RecomLevelEnum;
import com.gongyu.service.distribute.game.common.enums.SaleStatusEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.manager.UserExclusivePigManager;
import com.gongyu.service.distribute.game.mapper.PigGoodsMapper;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.util.DateUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/30 16:41
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserExclusivePigManager pigManager;
    @Autowired
    private AccountLogService logService;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private PigGoodsMapper goodsMapper;
    @Autowired
    private PigOrderService orderService;
    @Autowired
    private UserLevelService levelService;
    @Autowired
    private ConfigService configService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void processIncome(){
        List<ContractIncom> contractIncoms = new ArrayList<>();
        PigGoods maxPriceGoods = goodsMapper.findMaxPrice();
        //合约收入
//        BigDecimal conIncome = new BigDecimal("0");
        List<UserExclusivePig> pigs = pigManager.findListByUser();

        for(UserExclusivePig pig : pigs){
            Long day = DateUtil.getDay(DateUtils.format(DateUtil.getDate(pig.getBuyTime()), DateUtils.DEFAULT_DATE_TIME_FORMAT));
            if(day < 1){
                continue;
            }
            //计算精灵收益
            PigOrder order = orderService.getOne(new QueryWrapper<PigOrder>().eq("pig_id", pig.getId()));
            if(null == order){
                continue;
            }
            BigDecimal incom = this.calcuPigIncom(pig.getPigId(), order.getPigPrice());
//            conIncome = conIncome.add(incom);
            //精灵合约是否到期
            if(!DateUtil.before(DateUtils.format(DateUtil.getDate(pig.getEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT))){
                pig.setIsAbleSale(SaleStatusEnum.TRUE.getCode());
            }
            pig.setPrice(pig.getPrice().add(incom));
            //精灵是否分裂
            if(pig.getPrice().compareTo(maxPriceGoods.getLargePrice()) > 0){
                pigManager.splitPig(pig,maxPriceGoods);
            }
            pigManager.update(pig);
            Users user = usersService.getById(pig.getUserId());
            logService.convertAndInsert(user.getId(), new BigDecimal("0"),new BigDecimal("0"),0,incom,"合约收益",IncomeTypeEnum.CONTRACT_INCOM,pig.getPigId(),order.getPigOrderSn(),null);
            ContractIncom contractIncom = new ContractIncom();
            contractIncom.setUsers(user);
            contractIncom.setContractIncom(incom);
            contractIncoms.add(contractIncom);
            user.setAccumulatedIncome(user.getAccumulatedIncome().add(incom));
            user.setDogeMoney(user.getDogeMoney() + 1);
            usersService.updateById(user);
        }
        //计算上级提成收\团队提成收益
        ((IncomeServiceImpl)AopContext.currentProxy()).calcuIncome(contractIncoms);

    }

    /**
     * 计算上级提成收\团队提成收益
     * @param contractIncoms
     */
    public void calcuIncome(List<ContractIncom> contractIncoms){
        //推荐收益
        Config firstLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "first_rate"));
        Config secondLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "second_rate"));
        Config thirdLeader = configService.getOne(new QueryWrapper<Config>().eq("config_name", "third_rate"));
        for(ContractIncom incom : contractIncoms){
            Users user = incom.getUsers();
            Long firstLeaderId = user.getFirstLeader();
            if(null != firstLeaderId && firstLeaderId > 0){
                BigDecimal recomIncome = this.calcuRecomIncome(firstLeader.getConfigValue(), incom.getContractIncom());
                ((IncomeServiceImpl)AopContext.currentProxy()).updateLeaderIncome(firstLeaderId,recomIncome);
            }
            Long secondLeaderId = user.getSecondLeader();
            if(null != secondLeaderId && secondLeaderId > 0){
                BigDecimal recomIncome = this.calcuRecomIncome(secondLeader.getConfigValue(), incom.getContractIncom());
                ((IncomeServiceImpl)AopContext.currentProxy()).updateLeaderIncome(secondLeaderId,recomIncome);
            }
            Long thirdLeaderId = user.getThirdLeader();
            if(null != thirdLeaderId && thirdLeaderId > 0){
                BigDecimal recomIncome = this.calcuRecomIncome(thirdLeader.getConfigValue(), incom.getContractIncom());
                ((IncomeServiceImpl)AopContext.currentProxy()).updateLeaderIncome(thirdLeaderId,recomIncome);
            }


            if(null != firstLeaderId && firstLeaderId > 0){
                //计算团队收益
                ((IncomeServiceImpl)AopContext.currentProxy()).calcuTeamIncome(incom.getContractIncom(),user,firstLeaderId);
            }
        }
    }

    /**
     * 上级团队收益
     * @param
     * @param superLeaderId
     */
    public void calcuTeamIncome(BigDecimal income,Users user,Long superLeaderId){
        Users superLeader = usersService.getById(superLeaderId);
        UserLevel level = levelService.getById(superLeader.getLevel());
        LevelIncomEnum anEnum = LevelIncomEnum.parse(superLeader.getLevel());
        if(anEnum.isTeam()){
            if(superLeader.getLevel() > user.getLevel()){
                BigDecimal superIncom = income.multiply(level.getTeamAward()).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
                logService.convertAndInsert(superLeaderId, new BigDecimal("0"),new BigDecimal("0"),0,superIncom,"团队收益提成",IncomeTypeEnum.TEAM_WILL,0L,StringUtils.EMPTY,null);
                superLeader.setRecomIncome(superLeader.getRecomIncome().add(superIncom));
                usersService.updateById(superLeader);
            }
        }
        if(null != superLeader.getFirstLeader() && superLeader.getFirstLeader() > 0){
            this.calcuTeamIncome(income,user,superLeader.getFirstLeader());
        }
    }

    /**
     * 根据用户收益更新上级收益
     * @param leaderId
     * @param recomIncome
     */
    public void updateLeaderIncome(Long leaderId,BigDecimal recomIncome){
        Users leader = usersService.getById(leaderId);
        logService.convertAndInsert(leader.getId(),new BigDecimal("0"),new BigDecimal("0"),0,recomIncome,"推荐收益提成",IncomeTypeEnum.RECOMM_WILL,0L,StringUtils.EMPTY,null);
        leader.setRecomIncome(recomIncome.add(leader.getRecomIncome()));
        usersService.updateById(leader);
    }

    /**
     * 计算推广收益
     * @return
     */
    public BigDecimal calcuRecomIncome(String rate,BigDecimal income){
        BigDecimal incomeRate = new BigDecimal(rate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        BigDecimal multiply = income.multiply(incomeRate);
        return multiply;
    }

    /**
     * 计算pig合约收益
     * @param pigId 精灵ID
     * @param price 精灵现在价格
     * @return
     */
    public BigDecimal calcuPigIncom(Long pigId,BigDecimal price){
        PigGoods goods = goodsService.getById(pigId);
        //单日收益率
        BigDecimal divide = goods.getIncomeRatio().divide(new BigDecimal(String.valueOf(goods.getContractDays())),2,RoundingMode.HALF_UP);
        BigDecimal incomeRatio = divide.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
        BigDecimal income = price.multiply(incomeRatio);
        return income;
    }
}
