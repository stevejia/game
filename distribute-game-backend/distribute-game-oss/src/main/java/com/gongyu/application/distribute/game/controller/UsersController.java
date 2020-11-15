package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.common.enums.IncomeTypeEnum;
import com.gongyu.service.distribute.game.manager.PersonAuthRecordManager;
import com.gongyu.service.distribute.game.model.dto.*;
import com.gongyu.service.distribute.game.model.entity.*;
import com.gongyu.service.distribute.game.service.*;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("users")
@Api(tags = "会员管理")
public class UsersController {

    @Value("${register_url}")
    private String registerUrl;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserPaymentService userPaymentService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private DistributLevelService distributLevelService;
    @Autowired
    private UserExclusivePigService userExclusivePigService;
    @Autowired
    private PersonAuthRecordManager authRecordManager;

    @ApiOperation(value = "【会员管理】列表", notes = "【会员管理】列表", response = UsersResponseDto.class)
    @PostMapping("queryUsers")
    public BaseResponse queryUsers(Page page, @Valid @ModelAttribute UsersQueryDto usersQueryDto) {
    	List<Users> userList = usersService.queryUsers(page, usersQueryDto.getMobile(), usersQueryDto.getId(), usersQueryDto.getRegTimeStart(), usersQueryDto.getRegTimeEnd());
//        List<Users> userList = iPage.getRecords();
        List<UsersResponseDto> newList = Lists.newArrayList();
        userList.forEach(e -> {
            UsersResponseDto usersResponseDtoNew = UsersResponseDto.builder().build().convertToUser(e);
            // todo 计算总资产user_exclusive_pig  sum(price) 合约收益
            List<UserExclusivePig> list = userExclusivePigService.list(Wrappers.<UserExclusivePig>lambdaQuery().eq(UserExclusivePig::getUserId, usersResponseDtoNew.getId()).eq(UserExclusivePig::getIsPigLock, 0));
            BigDecimal reduce = list.stream().map(UserExclusivePig::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            usersResponseDtoNew.setTotalAssets(reduce);
            usersResponseDtoNew.setTotalProducts(list.size());
            List<Users> firstNum = usersService.list(new QueryWrapper<Users>().eq("first_leader", e.getId()));
            List<Users> twoNum = usersService.list(new QueryWrapper<Users>().eq("second_leader", e.getId()));
            List<Users> threeNum = usersService.list(new QueryWrapper<Users>().eq("third_leader", e.getId()));
            usersResponseDtoNew.setOneLowerLevel(null == firstNum ? 0L : firstNum.size());
            usersResponseDtoNew.setTwoLowerLevel(null == twoNum ? 0L : twoNum.size());
            usersResponseDtoNew.setThreeLowerLevel(null == threeNum ? 0L : threeNum.size());
            String url = String.format(registerUrl, e.getId());
            usersResponseDtoNew.setInviteCode(url);
            usersResponseDtoNew.setSuperiorId(e.getFirstLeader().intValue());
            usersResponseDtoNew.setExtensionAmount(e.getRecomIncome());
            usersResponseDtoNew.setContractualIncome(e.getAccumulatedIncome());
            usersResponseDtoNew.setCode(e.getCode());
            // todo 计算合约收益 account_log  type=21  sum(contract_revenue)
            newList.add(usersResponseDtoNew);
        });
        page.setRecords(newList);
        return BaseResponse.success(page);
    }

    @ApiOperation(value = "【会员管理】添加", notes = "【会员管理】添加")
    @PostMapping("saveUsers")
    @SysUserLog(module = "会员管理", action = "添加")
    public BaseResponse saveUsers(@Valid @ModelAttribute UsersSaveDto usersSaveDto) {
        usersService.saveUsers(usersSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【会员管理】修改", notes = "【会员管理】修改")
    @PostMapping("modifyUsersInfo")
    @SysUserLog(module = "会员管理", action = "修改")
    public BaseResponse modifyUsersInfo(@Valid @ModelAttribute UsersModifyDto usersModifyDto) {
        usersService.modifyUsersInfo(usersModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【会员管理】冻结&解冻操作", notes = "【会员管理】冻结&解冻操作")
    @PostMapping("lock")
    @SysUserLog(module = "会员管理", action = "冻结&解冻操作")
    public BaseResponse lock(@ApiParam(value = "userIds", required = true) @RequestParam() List<Long> userIds,
                             @ApiParam(value = "type 1冻结 2解冻", required = true) @RequestParam() Integer type) {
        usersService.lock(userIds, type);
        return BaseResponse.success(type == 1 ? "冻结成功" : "解冻成功");
    }

    @ApiOperation(value = "【会员管理】修改上级归属", notes = "【会员管理】修改上级归属")
    @PostMapping("modifyUsersFirstLeader")
    @SysUserLog(module = "会员管理", action = "修改上级归属")
    public BaseResponse modifyUsersFirstLeader(@ApiParam(value = "id", required = true) @RequestParam() Long id,
                                               @ApiParam(value = "firstLeader", required = true) @RequestParam() Integer firstLeader) {
        UsersModifyDto usersModifyDto = new UsersModifyDto();
        usersModifyDto.setId(id);
        usersModifyDto.setFirstLeader(firstLeader);
//        Users users = usersService.getById(firstLeader);
//        if (null != users && null != users.getFirstLeader()) {
//            usersModifyDto.setSecondLeader(users.getFirstLeader().intValue());
//            Users users1 = usersService.getById(users.getFirstLeader());
//            if (null != users1 && null != users1.getFirstLeader()) {
//                usersModifyDto.setThirdLeader(users1.getFirstLeader().intValue());
//            }
//        }
        usersService.modifyUsersFirstLeader(usersModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【会员管理】删除", notes = "【会员管理】删除")
    @PostMapping("deleteUsers")
    @SysUserLog(module = "会员管理", action = "删除")
    public BaseResponse deleteUsers(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        usersService.removeById(id);
        return BaseResponse.success("删除成功");
    }

    @ApiOperation(value = "【会员管理】详情", notes = "【会员管理】详情", response = Users.class)
    @PostMapping("getUsers")
    public BaseResponse getUsers(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        Users byId = usersService.getById(id);
        PersonAuthRecord record = authRecordManager.findByUserId(byId.getId());
        if (null != record) {
            byId.setIdentity(record.getIdCard());
        }
        //  查询合伙人
        UserTeamDto userTeamDto = usersService.getTeamLevelNum(id);
        BeanUtils.copyProperties(userTeamDto, byId);
        return BaseResponse.success(byId);
    }

    @ApiOperation(value = "【会员管理】银行卡信息详情", notes = "【会员管理】银行卡信息详情", response = UserPayment.class)
    @PostMapping("getBankDetail")
    public BaseResponse getBankDetail(@ApiParam(value = "userId", required = true) @RequestParam() Long userId) {
        List<UserPayment> list = userPaymentService.list(Wrappers.<UserPayment>lambdaQuery().eq(UserPayment::getUserId, userId));
        return BaseResponse.success(list);
    }

    @ApiOperation(value = "【会员管理】资金记录", notes = "【会员管理】资金记录", response = AccountLog.class)
    @PostMapping("queryAccountLog")
    public BaseResponse queryAccountLog(Page page, @ApiParam(value = "userId") Long userId,
                                        @ApiParam(value = "mobile") String mobile) {
        return BaseResponse.success(accountLogService.queryAccountLog(page, userId, mobile));
    }

    @ApiOperation(value = "【会员管理】资金调节", notes = "【会员管理】资金调节", response = AccountLog.class)
    @PostMapping("modifyAccountScore")
    public BaseResponse modifyAccountScore(@ApiParam(value = "userId") Integer userId,
                                           @ApiParam(value = "score") BigDecimal score,
                                           @ApiParam(value = "type  1推广收益 2积分") int type,
                                           @ApiParam(value = "direction 1增加积分 2减少积分") int direction,
                                           @ApiParam(value = "twoPassword ") String twoPassword,
                                           @ApiParam(value = "remark") String remark) {
        Users byId = usersService.getById(userId);
        if (byId == null) {
            throw new BizException("充值用户不存在");
        }
        usersService.modifyAccount(userId, type, score, direction, remark, IncomeTypeEnum.PROMOTE);
        return BaseResponse.success("调节成功");
    }

    @ApiOperation(value = "【会员管理】首页统计数据", notes = "【会员管理】首页统计数据", response = HomeDto.class)
    @PostMapping("home")
    public BaseResponse home() {
        return BaseResponse.success(usersService.home());
    }

    @ApiOperation(value = "【会员管理】新增会员趋势图", notes = "【会员管理】新增会员趋势图")
    @PostMapping("newUsers")
    public BaseResponse newUsers(@ApiParam(value = "startDate 例如2020-07-20") String startDate,
                                 @ApiParam(value = "endDate 例如2020-07-21") String endDate) {
        return BaseResponse.success(usersService.newUsers(startDate, endDate));
    }
    
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @PostMapping("queryAllUsers")
    public BaseResponse queryAllUsers() {
    	List<Users> allUsers = usersService.queryAllUsers();
    	List<UsersResponseDto> newList = Lists.newArrayList();
    	allUsers.forEach(user->{
    		UsersResponseDto resUser = new UsersResponseDto();
    		resUser.setId(user.getId());
    		resUser.setCode(user.getCode());
    		resUser.setNickname(user.getNickname());
    		resUser.setMobile(user.getMobile());
    		newList.add(resUser);
    	});
    	
        return BaseResponse.success(newList);
    }
}