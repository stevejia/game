package com.gongyu.application.distribute.game.controller;

import com.gongyu.service.distribute.game.common.enums.PhoneCodeTypeEnum;
import com.gongyu.service.distribute.game.model.dto.MemberLoginRequestDto;
import com.gongyu.service.distribute.game.model.dto.MemberLoginResponseDto;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.UsersService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@Api(tags = "APP会员")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "登录", response = MemberLoginResponseDto.class)
    @PostMapping("/login")
    public Object login(@Valid @ApiParam("手机号") String mobile,
                        @ApiParam("图形密码") String password,
                        @ApiParam("图形验证码Key") String codeKey,
                        @ApiParam("图形验证码") String code) {
        MemberLoginResponseDto memberLoginResponseDto = usersService.login(mobile, password, codeKey,
                code, WebUtils.getClientIp());
        //RedisUtils.set(memberLoginResponseDto.getToken(), memberLoginResponseDto.getUserId());
        WebUtils.setCurrentUserId(memberLoginResponseDto.getUserId());
        return BaseResponse.success(memberLoginResponseDto);
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Object logout() {
        usersService.logout();
        WebUtils.setCurrentUserId(null);
        return BaseResponse.success("退出账户成功");
    }

    @ApiOperation(value = "注册", response = Users.class)
    @PostMapping("/reg")
    public Object reg(@Valid @ModelAttribute MemberLoginRequestDto memberLoginRequestDto) {
        usersService.create(memberLoginRequestDto);
        return BaseResponse.success(usersService.login(memberLoginRequestDto.getMobile()));
    }

    @ApiOperation(value = "查询用户信息", response = Users.class)
    @PostMapping("/queryUser")
    public BaseResponse queryUser() {
        usersService.queryUser(WebUtils.getCurrentUserId());
        return BaseResponse.success();
    }

    @ApiOperation(value = "设置用户信息", response = Users.class)
    @PostMapping("/updateUser")
    public Object updateUser(@ApiParam("id 用户ID") Long id,
                             @ApiParam("userName 用户名") String userName) {
        usersService.updateUser(id, userName);
        return BaseResponse.success();
    }

    @ApiOperation(value = "获取图形验证码", notes = "获取图形验证码")
    @PostMapping(value = "getGraphVerifyCode")
    public BaseResponse getGraphVerifyCode() {
        return BaseResponse.success(usersService.getGraphVerifyCode());
    }

    @ApiOperation(value = "验证图形验证码", notes = "验证图形验证码")
    @PostMapping(value = "checkGraphVerifyCode")
    public BaseResponse checkGraphVerifyCode(@ApiParam("图形验证码Key") String codeKey,
                                             @ApiParam("图形验证码") String code) {
        String graphVerifyCode = RedisUtils.get(codeKey);
        if (StringUtils.isEmpty(graphVerifyCode)) {
            throw new BizException("图形验证码失效");
        }
        if (!code.equals(graphVerifyCode)) {
            throw new BizException("图形验证码错误");
        }
        return BaseResponse.success("图形验证成功");
    }

    @ApiOperation(value = "发送短信")
    @PostMapping("/sendCode")
    public BaseResponse sendCode(@ApiParam(value = "mobile", required = true) @RequestParam() String mobile,
                                 @ApiParam(value = "codeType REGISTER=注册；LOGIN=登录；" +
                                         "FG_PWD=修改密码；FG_PPWD=修改支付密码;COLLECT_MONEY=收款方式", required = true) @RequestParam() String codeType,
                                 @ApiParam("图形验证码Key") String codeKey,
                                 @ApiParam("图形验证码") String code) {
        if (!codeType.equals(PhoneCodeTypeEnum.COLLECT_MONEY.getCode())) {
            if (StringUtils.isEmpty(codeKey)) {
                throw new BizException("请输入图形验证码");
            }
            if (StringUtils.isEmpty(code)) {
                throw new BizException("请输入图形验证码");
            }
            String graphVerifyCode = RedisUtils.get(codeKey);
            if (StringUtils.isEmpty(graphVerifyCode)) {
                throw new BizException("图形验证码失效");
            }
            if (!code.equals(graphVerifyCode)) {
                throw new BizException("图形验证码错误");
            }
        }
//        String SmsTemplate = "【航海世界】尊敬的用户，您的验证码为code,请勿告诉他人。";
//        String smsCode = RandomStringUtils.randomNumeric(4);
//        String content = SmsTemplate.replace("code", smsCode);
//        usersService.sendSmsCodeThirdParty(mobile, codeType, content, smsCode);
        final String SmsTemplateCode = "SMS_201653101";
        String smsCode = RandomStringUtils.randomNumeric(4);
//        String content = SmsTemplate.replace("code", smsCode);
        final Map<String, String> templateParam = new HashMap<String, String>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
                put("code", smsCode);
            }
        };
        usersService.sendSmsCode(mobile, codeType, SmsTemplateCode, templateParam);
        return BaseResponse.success();
    }

    @ApiOperation(value = "忘记密码")
    @PostMapping("/fgPwd")
    public BaseResponse fgPwd(@Valid @ModelAttribute MemberLoginRequestDto memberLoginRequestDto) {
    	memberLoginRequestDto.setUId(WebUtils.getCurrentUserId());
        usersService.fgPwd(memberLoginRequestDto);
        return BaseResponse.success();
    }

    @ApiOperation(value = "下载地址")
    @PostMapping("/downUrl")
    public BaseResponse downUrl(@ApiParam(value = "sysType 1=苹果；2=安卓", required = true) @RequestParam() Integer sysType) {
        return BaseResponse.success(usersService.downUrl(sysType));
    }


}