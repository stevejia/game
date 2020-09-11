package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.AccountLog;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import com.gongyu.service.distribute.game.service.MyService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/18 18:25
 */
@RestController
@Api(tags = "我的信息")
@RequestMapping("my")
public class MyController {

    @Autowired
    private MyService myService;

    @ApiOperation(value = "获取(我的)用户信息", response = MyUserDataDto.class)
    @PostMapping("getUser")
    public BaseResponse getUserBaseData() {
        return myService.getMyBaseData(WebUtils.getCurrentUserId());
    }

    /**
     * @param page
     * @return
     */
    @ApiOperation(value = "累计收益", response = AccountLog.class)
    @PostMapping("income")
    public BaseResponse income(Page page, @Valid IncomeQueryReqDto param) {
        return myService.accumIncome(page, WebUtils.getCurrentUserId(), param.getType());
    }

    @ApiOperation(value = "出售推广收益", response = BaseResponse.class)
    @PostMapping("sell")
    public BaseResponse sell(@Valid SellIncomeReqDto param) {
        return myService.sellIncome(param,WebUtils.getCurrentUserId());
    }

    @ApiOperation(value = "领养记录", response = AdoptRecordDto.class)
    @PostMapping("adopt")
    public BaseResponse adoptRecord(Page page,AdoptReqDto param) {
        return myService.adoptRecord(page,param, WebUtils.getCurrentUserId());
    }

    @ApiOperation(value = "转让记录", response = TransfResultDto.class)
    @PostMapping("transf")
    public BaseResponse transf(Page page,TransfReqDto param) {
        return myService.transf(page,param, WebUtils.getCurrentUserId());
    }

    @ApiOperation(value = "申诉提交", response = BaseResponse.class)
    @PostMapping("appeal")
    public BaseResponse appeal(AppealReqDto param) {
        return myService.appeal(param);
    }

    @ApiOperation(value = "预约记录", response = ReserveResultDto.class)
    @PostMapping("reserve")
    public BaseResponse reserve(Page page) {
        return myService.reserve(page, WebUtils.getCurrentUserId());
    }

    @ApiOperation(value = "收账方式", response = UserPayment.class)
    @PostMapping("collectList")
    public BaseResponse collectList() {
        return myService.collectList(WebUtils.getCurrentUserId());
    }

    @ApiOperation(value = "添加修改收款方式", response = BaseResponse.class)
    @PostMapping("addUpdate")
    public BaseResponse addUpdate(UserPayment payment) {
        return myService.addUpdate(payment);
    }

    @ApiOperation(value = "实名认证", response = BaseResponse.class)
    @PostMapping("auth")
    public BaseResponse auth(AuthReqDto param) {
        String checkMsg = param.checkVerfCode();
        if(StringUtils.isNotBlank(checkMsg)){
            return BaseResponse.error(checkMsg);
        }
        return myService.auth(param);
    }


    @ApiOperation(value = "获取推广码url",response = RecomCodeResultDto.class)
    @PostMapping("recomCode")
    public BaseResponse recomCode(){
        return myService.recomCode(WebUtils.getCurrentUserId());
    }


    @ApiOperation(value = "我的团队",response = MyTeamResultDto.class)
    @PostMapping("myTeam")
    public BaseResponse myTeam(){
        return myService.myTeam(WebUtils.getCurrentUserId());
    }


    @ApiOperation(value = "检查是否拥有合法收款方式",response = BaseResponse.class)
    @PostMapping("checkedPayment")
    public BaseResponse checkedPayment(){
        return myService.checkedPayment(WebUtils.getCurrentUserId());
    }


    @ApiOperation(value = "检查账号是否做过实名认证",response = BaseResponse.class)
    @PostMapping("checkedProAuth")
    public BaseResponse checkedProAuth(){
        return myService.checkedProAuth(WebUtils.getCurrentUserId());
    }
}
