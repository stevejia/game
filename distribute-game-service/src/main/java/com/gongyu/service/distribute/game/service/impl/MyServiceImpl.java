package com.gongyu.service.distribute.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.*;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.manager.*;
import com.gongyu.service.distribute.game.mapper.PigReservationMapper;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.util.DateUtils;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/18 18:41
 */
@Slf4j
@Service
public class MyServiceImpl implements MyService {

    @Value("${register_url}")
    private String registerUrl;

    @Autowired
    private UsersService usersService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private PigGoodsService goodsService;
    @Autowired
    private SellRecordManager sellRecordManager;
    @Autowired
    private AppealMangaer appealMangaer;
    @Autowired
    private PigOrderManager orderManager;
    @Autowired
    private UserExclusivePigDelManager pigDelManager;
    @Autowired
    private PigReservationMapper reservationMapper;
    @Autowired
    private MyManager myManager;
    @Autowired
    private UserExclusivePigManager pigManager;
    @Autowired
    private UserExclusivePigService pigService;
    @Autowired
    private UserPaymentService paymentService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private PersonAuthRecordManager authRecordManager;
    @Autowired
    private AlPersonAuthManager alPersonAuthManager;
    @Autowired
    private UserIdentityService identityService;

    @Autowired
    private PigOrderService orderService;
    @Override
    public BaseResponse getMyBaseData(Long userId) {
        Users users = usersService.getById(userId);
        List<UserExclusivePig> pigs = pigService.list(new QueryWrapper<UserExclusivePig>().eq("user_id",userId).eq("is_pig_lock", LockStatusEnum.NOT_LOCK.getCode()));
        List<PigOrder> orders = orderService.list(new QueryWrapper<PigOrder>().eq("sell_user_id", userId).eq("sell_confirm_status",CommEnum.FALSE.getCode()));
        if(!CollectionUtils.isEmpty(orders)){
            orders.stream().forEach(item -> {
                UserExclusivePig pig = pigService.getById(item.getPigId());
                if(userId.longValue() != pig.getUserId().longValue()){
                    pigs.add(pig);
                }
            });
        }
//        Iterator<UserExclusivePig> iterator = pigs.iterator();
//        while (iterator.hasNext()){
//            UserExclusivePig pig = iterator.next();
//            if(null == pig){
//                iterator.remove();
//            }
//            if(!DateUtil.before(DateUtils.format(DateUtil.getDate(pig.getEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT))){
//                iterator.remove();
//            }
//        }
        MyUserDataDto result = myManager.convertUserDataResult(users,pigs);
        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse accumIncome(IPage page,Long userId,Integer type) {
        List<AccountLogDto> list = accountLogService.findPage(page, userId, String.valueOf(type));
        page.setRecords(list);
        return BaseResponse.success(page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BaseResponse sellIncome(SellIncomeReqDto param,Long userId) {
        Users users = usersService.getById(userId);
        Assert.notNull(users,"没有找到该用户");
        Config config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "limit_day_sale_times"));
        List<SellRecord> records = sellRecordManager.findByUserAndNow(WebUtils.getCurrentUserId());
        if(Integer.valueOf(config.getConfigValue()) <= records.size()){
            return BaseResponse.error("每天可出售" + config.getConfigValue() + "次");
        }

        config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "user_sell_usermoney_ratio"));
        BigDecimal divide = new BigDecimal(config.getConfigValue()).divide(new BigDecimal("100"));
        BigDecimal multiply = users.getRecomIncome().multiply(divide);
        if(multiply.compareTo(param.getSellNum()) < 0){
            return BaseResponse.error("出售数量不能超过" + multiply);
        }
        config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "min_sell_usermoney"));
        if(new BigDecimal(config.getConfigValue()).compareTo(param.getSellNum()) > 0){
            return BaseResponse.error("出售收益不得少于" + config.getConfigValue());
        }
        config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "day_buypig_limit"));
        if(config.getConfigValue().equals("1")){
            List<UserExclusivePig> pigs = pigService.list(new QueryWrapper<UserExclusivePig>().eq("user_id",userId).eq("is_able_sale", SaleStatusEnum.FALSE.getCode()).eq("is_pig_lock", LockStatusEnum.NOT_LOCK.getCode()));

            Iterator<UserExclusivePig> iterator = pigs.iterator();
            while (iterator.hasNext()){
                UserExclusivePig pig = iterator.next();
                if(null == pig){
                    iterator.remove();
                }
                if(!DateUtil.before(DateUtils.format(DateUtil.getDate(pig.getEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT))){
                    iterator.remove();
                }
            }

            BigDecimal asset = new BigDecimal("0");
            for(UserExclusivePig pig : pigs){
                asset = asset.add(null == pig.getPrice() ? new BigDecimal("0") : pig.getPrice());
            }
            if(param.getSellNum().compareTo(asset) > 0){
                return BaseResponse.error("不得大于总资产");
            }

        }

        if(!StringUtils.equals(users.getPaypwd(),DigestUtils.md5Hex(param.getPayPwd()))){
            return BaseResponse.error("交易密码错误");
        }
        List<PigGoods> list = goodsService.list();
        Collections.sort(list);
        PigGoods goods = list.stream()
                .filter(item -> item.getSmallPrice().compareTo(param.getSellNum()) < 1 && item.getLargePrice().compareTo(param.getSellNum()) > 0)
                .findFirst()
                .orElseThrow(() -> new BizException("出售金额应在" + list.get(0).getSmallPrice() + "~" + list.get(list.size() -1).getLargePrice() + "之间"));
        users.setRecomIncome(users.getRecomIncome().subtract(param.getSellNum()));
        usersService.updateById(users);
        accountLogService.convertAndInsert(userId,new BigDecimal("0"),new BigDecimal("0"),0,param.getSellNum().negate(),"出售推广收益",IncomeTypeEnum.SELL_INCOM,goods.getId(), "",null);
        sellRecordManager.convertAndInsert(userId,param.getSellNum(),goods.getId());
        UserExclusivePig pig = pigManager.convert(userId, goods.getId(), SaleStatusEnum.TRUE, param.getSellNum(), null, null, DateUtil.getNowDate(), DateUtil.getNowDate(), BuyTypeEnum.SELL_INCOM.getCode(), goods.getContractDays(), goods.getIncomeRatio(), LockStatusEnum.NOT_LOCK);
        pigManager.insert(pig);
        return BaseResponse.success(users);
    }

    @Override
    public BaseResponse adoptRecord(IPage page,AdoptReqDto param, Long userId) {
        if(TakeQueryEnum.TAKE_ING.getCode() == param.getQueryType()){
            return BaseResponse.success(this.getOrderRecord(param, userId,page));
        }
        if(TakeQueryEnum.TAKEED.getCode() == param.getQueryType()){
            return BaseResponse.success(this.getContracted(param, userId,page));
        }

        if(TakeQueryEnum.APPEAL.getCode() == param.getQueryType()){
            return BaseResponse.success(this.getAppealRecord(userId,page));
        }
        if(TakeQueryEnum.SPLIT.getCode() == param.getQueryType()){
            return BaseResponse.success(this.getSplitRecord(userId, page));
        }
        return BaseResponse.error("不能被解析的查询类型");
    }

    @Override
    public BaseResponse transf(IPage page,TransfReqDto param, Long userId) {
        TransfQueryEnum queryEnum = TransfQueryEnum.parse(param.getQueryType());
        log.info("MyService transf queryEnum:{}", JSON.toJSONString(queryEnum));
        if(TransfQueryEnum.NOT_TRANSF.equals(queryEnum)){
            return BaseResponse.success(this.getEtcTransf(page,userId,param));
        }
        if(TransfQueryEnum.TRANSF_ING.equals(queryEnum) || TransfQueryEnum.TRANSGED.equals(queryEnum)){
            return BaseResponse.success(this.getTransfPage(page, userId, param));
        }
        if(TransfQueryEnum.APPEAL.equals(queryEnum)){
            return BaseResponse.success(this.getAppealRecord(userId,page));
        }
        return BaseResponse.error("不能被解析的查询类型");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BaseResponse appeal(AppealReqDto param) {
        PigOrder order = orderManager.getByOrderNo(param.getOrderNo());
        Assert.notNull(order,"没有找到所属订单");
        appealMangaer.convertAndInsert(order,AppealStatusEnum.NOT_AUDIT,param);
        order.setAppealTime(DateUtil.getNowDate());
        order.setPayStatus(PayStatusEnum.FREEZE.getCode());
        orderManager.updateIgnoreNull(order);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse reserve(IPage page, Long userId) {
        List<ReserveResultDto> resultDtos = new ArrayList<>();
        List<PigReservation> list = reservationMapper.findPageByUser(page,userId);
        for(PigReservation reservation : list){
            PigGoods goods = goodsService.getById(reservation.getPigId());
            ReserveResultDto convertDto = myManager.convertReserveResult(goods, reservation);
            resultDtos.add(convertDto);
        }
        page.setRecords(resultDtos);
        return BaseResponse.success(page);
    }

    @Override
    public BaseResponse collectList(Long userId) {
        List<UserPayment> list = paymentService.list(new QueryWrapper<UserPayment>().eq("user_id", userId));
        return BaseResponse.success(list);
    }

    @Override
    public BaseResponse addUpdate(UserPayment payment) {
        if(null != payment.getId()){
            UserPayment paymentRecord = paymentService.getById(payment.getId());
            Assert.notNull(payment,"没找到对应的收款方式记录");
            BeanUtils.copyProperties(payment,paymentRecord);
            paymentRecord.setStatus(CommEnum.FALSE.getCode());
            paymentRecord.setCreateTime(DateUtil.getNowDate());
            paymentService.updateById(paymentRecord);
        }else{
            UserPayment userPayment = paymentService.getOne(new QueryWrapper<UserPayment>().eq("type", payment.getType()).eq("user_id", payment.getUserId()));
            if(null != userPayment && (CommEnum.FALSE.getCode() == userPayment.getStatus() || CommEnum.TRUE.getCode() == userPayment.getStatus())){
                return BaseResponse.error("已有" + PaymentTypeEnum.parse(payment.getType()).getDesc() + "收款方式");
            }
            payment.setStatus(CommEnum.TRUE.getCode());
            payment.setCreateTime(DateUtil.getNowDate());
            paymentService.save(payment);
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse auth(AuthReqDto param) {
        PersonAuthRecord record = null;
        try{
            Config config = configService.getOne(new QueryWrapper<Config>().eq("config_name", "bind_identity_maxnum"));
            List<PersonAuthRecord> authRecords = authRecordManager.findByPerson(param.getPropleName(), param.getIdCard());
            if(!CollectionUtils.isEmpty(authRecords) && Integer.parseInt(config.getConfigValue()) <= authRecords.size()){
                return BaseResponse.error("最多只能绑定" + config.getConfigValue() + "个账户");
            }
            PersonAuthRecord authRecord = authRecordManager.findByUserId(WebUtils.getCurrentUserId());
            if(null != authRecord && AuthStatusEnum.AUTH_SUCCESS.getCode() == authRecord.getCertifStatus()){
                return BaseResponse.success(AuthStatusEnum.AUTH_SUCCESS.getDesc());
            }
            record = authRecordManager.convert(param.getPropleName(), param.getIdCard(),WebUtils.getCurrentUserId());
            authRecordManager.insert(record);
            AuthPersonResultDto resultDto = alPersonAuthManager.checkPersonAuth(param.getIdCard(), param.getPropleName());
            record.setAlResultJson(JSON.toJSONString(resultDto));
            record.setReqStatus(AlRespStatusEnum.RESP_SUCCESS.getCode());

            if("1".equals(resultDto.getRes())){
                record.setCertifStatus(AuthStatusEnum.AUTH_SUCCESS.getCode());
            }else{
                record.setCertifStatus(AuthStatusEnum.AUTH_FAIL.getCode());
                return BaseResponse.error("认证失败");
            }
            UserIdentity identity = identityService.convertUserIdentity(WebUtils.getCurrentUserId(), param.getPropleName(), param.getIdCard());
            identityService.save(identity);
            authRecordManager.updateIgnoreNull(record);
            return BaseResponse.success("验证通过");
        }catch (ServiceException e){
            log.info("MyServiceImpl auth throw ServiceException ... ",e);
            if(null != record){
                record.setReqStatus(AlRespStatusEnum.PUSH_FAIL.getCode());
                authRecordManager.updateIgnoreNull(record);
            }
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.info("MyServiceImpl auth throw ServiceException ... ",e);
            if(null != record){
                record.setReqStatus(AlRespStatusEnum.PUSH_FAIL.getCode());
                authRecordManager.updateIgnoreNull(record);
            }
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse recomCode(Long userId) {
        RecomCodeResultDto resultDto = new RecomCodeResultDto();
        Users users = usersService.getById(userId);
        String url = String.format(registerUrl, users.getId());

        List<Config> list = configService.list(new QueryWrapper<Config>().eq("config_name", "share_image"));
        int i = new Random().nextInt(list.size());
        Config config = list.get(i);
        resultDto.setBackImgUrl(config.getConfigValue());
        resultDto.setInviteCodeUrl(url);
        return BaseResponse.success(resultDto);
    }

    @Override
    public BaseResponse myTeam(Long userId){
        //直推
        List<Users> firstUsers = usersService.list(new QueryWrapper<Users>().eq("first_leader", userId));
        List<Users> secondList = usersService.list(new QueryWrapper<Users>().eq("second_leader", userId));
        List<Users> thirdList = usersService.list(new QueryWrapper<Users>().eq("third_leader", userId));
        MyTeamResultDto resultDto = this.convertTeamResult(firstUsers, secondList, thirdList);
        return BaseResponse.success(resultDto);
    }

    @Override
    public BaseResponse checkedPayment(Long userId) {
        List<UserPayment> list = paymentService.list(new QueryWrapper<UserPayment>().eq("user_id", userId).eq("status", CommEnum.TRUE.getCode()));
        if(CollectionUtils.isEmpty(list)){
            return BaseResponse.error("验证失败");
        }
        return BaseResponse.success("验证通过");
    }

    @Override
    public BaseResponse checkedProAuth(Long userId) {
        PersonAuthRecord authRecord = authRecordManager.findByUserId(WebUtils.getCurrentUserId());
        if(null != authRecord){
            return BaseResponse.error("已做过实名认证");
        }
        return BaseResponse.success("检查通过");
    }

    public MyTeamResultDto convertTeamResult(List<Users> firstUsers,List<Users> secondList,List<Users> thirdList){
        MyTeamResultDto resultDto = new MyTeamResultDto();
        //直推用户dto
        List<MyTeamResultDto.User> resultUsers = new LinkedList<>();
        //正式用户dto
        MyTeamResultDto.TeamUserNum teamUserNum = new MyTeamResultDto.TeamUserNum(0,0,0,0,0,0);
        List<Users> firstValidNum = new LinkedList<>();
        List<Users> firstInvalidNum = new LinkedList<>();
        List<Users> twoValidNum = new LinkedList<>();
        List<Users> twoInvalidNum = new LinkedList<>();
        List<Users> threeValidNum = new LinkedList<>();
        List<Users> threeInvalidNum = new LinkedList<>();

        for(Users u : firstUsers){
            MyTeamResultDto.User resultUser = new MyTeamResultDto.User();
            resultUser.setNickName(u.getNickname());
            resultUser.setUserId(u.getId());
            resultUsers.add(resultUser);
        }
        this.handleUserNum(firstUsers,firstValidNum,firstInvalidNum);
        this.handleUserNum(secondList,twoValidNum,twoInvalidNum);
        this.handleUserNum(thirdList,threeValidNum,threeInvalidNum);
        teamUserNum.setFirstValidNum(firstValidNum.size());
        teamUserNum.setFirstInvalidNum(firstInvalidNum.size());
        teamUserNum.setTwoValidNum(twoValidNum.size());
        teamUserNum.setTwoInvalidNum(twoInvalidNum.size());
        teamUserNum.setThreeValidNum(threeValidNum.size());
        teamUserNum.setThreeInvalidNum(threeValidNum.size());
        resultDto.setUsers(resultUsers);
        resultDto.setUserNum(teamUserNum);
        return resultDto;
    }

    public void handleUserNum(List<Users> users,List<Users> vailds,List<Users> invailds){
        for(Users u : users){
            PigOrder order = orderManager.findByBuyUser(u.getId());
            PigOrder sellOrder = orderManager.findBySellUser(u.getId());
            if(null == order || null == sellOrder){
                invailds.add(u);
            } else if(1 == order.getSellConfirmStatus() && order.getEndTime() > 0 && null != sellOrder && sellOrder.getSellConfirmStatus() == 1){
                vailds.add(u);
            }
        }
    }

    public IPage getEtcTransf(IPage page,Long userId,TransfReqDto param){
        List<TransfResultDto> resultDtos = new ArrayList<>();
        List<UserExclusivePig> pigs = pigManager.findPageByUser(page, userId,SaleStatusEnum.TRUE, param);
        for(UserExclusivePig pig : pigs){
            PigGoods goods = goodsService.getById(pig.getPigId());
            PigOrder order = orderManager.getById(pig.getOrderId());
            TransfResultDto dto = myManager.convertTransfByEtc(pig, goods,order);
            resultDtos.add(dto);
        }
        page.setRecords(resultDtos);
        return page;
    }

    public IPage getTransfPage(IPage page,Long userId,TransfReqDto param){
        List<TransfResultDto> resultDtos = new ArrayList<>();
        List<PigOrder> list = orderManager.findPageBySell(page, userId,param);
        for(PigOrder order : list){
            PigGoods goods = goodsService.getById(order.getPigLevel());
            Users users = usersService.getById(order.getPurchaseUserId());
            TransfResultDto dto = myManager.convertTransf(order, goods,null,users.getNickname());
            resultDtos.add(dto);
        }
        page.setRecords(resultDtos);
        return page;
    }

    public IPage getSplitRecord(Long userId,IPage page){
        List<AdoptRecordDto> resultDtos = new LinkedList<>();
        List<UserExclusivePigDel> list = pigDelManager.findPageByUser(page, userId);
        for(UserExclusivePigDel pigDel : list){
            PigGoods goods = goodsService.getById(pigDel.getPigId());
            PigOrder order = orderManager.getById(pigDel.getOrderId());
            if(null == order){
                continue;
            }
            UserExclusivePig pig = new UserExclusivePig();
            BeanUtils.copyProperties(pigDel,pig);
            AdoptRecordDto dto = myManager.convertAdoptResult(order, goods, pig);
            resultDtos.add(dto);
        }
        page.setRecords(resultDtos);
        return page;
    }

    public IPage getAppealRecord(Long userId,IPage page){
        List<TransfResultDto> resultDtos = new ArrayList<>();
        List<PigAppeal> list = appealMangaer.findPage(page, userId);
        for(PigAppeal appeal : list){
            PigOrder order = orderManager.getById(appeal.getOrderId());
            PigGoods goods = goodsService.getById(order.getPigLevel());
            TransfResultDto resultDto = myManager.convertTransf(order, goods, appeal,StringUtils.EMPTY);
            resultDtos.add(resultDto);
        }
        page.setRecords(resultDtos);
        return page;
    }

    public IPage getContracted(AdoptReqDto param, Long userId,IPage page){
        List<AdoptRecordDto> resultDtos = new LinkedList<>();
        List<UserExclusivePig> pigs = pigManager.findPageByUser(page, userId,SaleStatusEnum.FALSE, new TransfReqDto());
        for(UserExclusivePig pig : pigs){
            PigGoods goods = goodsService.getById(pig.getPigId());
            PigOrder order = orderService.getOne(new QueryWrapper<PigOrder>().eq("pig_id",pig.getId()));
            if(null != order && CommEnum.FALSE.getCode() == order.getSellConfirmStatus()){
                continue;
            }
            if(null == pig){
                continue;
            }
            if(LockStatusEnum.LOCK.getCode().intValue() == pig.getIsPigLock().intValue()){
                continue;
            }
            if(!DateUtil.before(DateUtils.format(DateUtil.getDate(pig.getEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT))){
                continue;
            }
            AdoptRecordDto dto = myManager.convertAdoptResult(order, goods, pig);
            resultDtos.add(dto);
        }
        page.setRecords(resultDtos);
        return page;
    }

    public IPage getOrderRecord(AdoptReqDto param, Long userId,IPage page){
        List<AdoptRecordDto> resultDtos = new LinkedList<>();
        List<PigOrder> list = orderManager.findPageByUser(page, userId, param);
        for(PigOrder order : list){
            PigGoods goods = goodsService.getById(order.getPigLevel());
            UserExclusivePig pig = pigManager.findById(order.getPigId());
            if(null == pig){
                continue;
            }
            if(LockStatusEnum.LOCK.getCode().intValue() == pig.getIsPigLock().intValue()){
                continue;
            }
            AdoptRecordDto dto = myManager.convertAdoptResult(order, goods, pig);
            resultDtos.add(dto);
        }
        page.setRecords(resultDtos);
        return page;
    }




}
