package com.futures.service.impl;

import com.futures.common.enums.PhoneCodeTypeEnum;
import com.futures.common.enums.YesOrNoEnum;
import com.futures.config.SmsClientSend;
import com.futures.mapper.PhoneCodeMapper;
import com.futures.model.dto.PhoneCodeDto;
import com.futures.model.entity.PhoneCode;
import com.futures.service.SysSmsFpi;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.sms.aliyun.AliyunSmsConfig;
import com.gongyu.snowcloud.framework.sms.aliyun.AliyunSmsSender;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

@Component
public class SysSmsFpiImpl extends CrudServiceSupport<PhoneCodeMapper, PhoneCode> implements SysSmsFpi {
    private static final Logger logger = LoggerFactory.getLogger(SysSmsFpiImpl.class);
    @Autowired
    private PhoneCodeMapper phoneCodeMapper;
    @Autowired
    private AliyunSmsConfig aliyunSmsConfig;
    @Autowired
    private AliyunSmsSender aliyunSmsSender;
    @Autowired
    private SmsClientSend smsClientSend;

    public SysSmsFpiImpl() {
    }

    private static Date getDelayMinute(int minutes) {
        return Date.from(LocalDateTime.now().plusMinutes((long) minutes).toInstant(ZoneOffset.ofHours(8)));
    }

    @Transactional(
            rollbackFor = {Exception.class, RuntimeException.class}
    )
    public boolean checkSmsCode(String phone, String code, String codeType) throws BizException {
        PhoneCode phoneCode = this.phoneCodeMapper.queryByPhoneType(phone, codeType);
        if (null == phoneCode) {
            throw new BizException("请先发送短信验证码");
        } else if (!StringUtils.equals(code, phoneCode.getCode())) {
            logger.info(phone + " db code:{}, param code:{}", phoneCode.getCode(), code);
            return false;
        } else {
            phoneCode.setBeUsed(YesOrNoEnum.Y.getCode());
            updateById(phoneCode);
            return true;
        }
    }

    public PhoneCode checkSmsCodeForNext(String phone, String code, String codeType) throws BizException {
        PhoneCode phoneCode = this.phoneCodeMapper.queryByPhoneType(phone, codeType);
        if (null == phoneCode) {
            throw new BizException("请先发送短信验证码");
        } else if (!StringUtils.equals(code, phoneCode.getCode())) {
            logger.info(phone + " db code:{}, param code:{}", phoneCode.getCode(), code);
            return null;
        } else {
            return phoneCode;
        }
    }

    public String getCode(Integer num) throws BizException {
        return RandomStringUtils.randomNumeric(num);
    }

    @Transactional(
            rollbackFor = {Exception.class, RuntimeException.class}
    )
    public PhoneCodeDto sendSmsCode(String phone, String codeType, String SmsTemplate, Map<String, String> templateParam) {
        String code = null;
        PhoneCodeDto phoneCodeDto = new PhoneCodeDto();
        phoneCodeDto.setExpiresTime(SMS_EXPIRES_TIME);
        Assert.notNull(PhoneCodeTypeEnum.getEnum(codeType), "验证码类型错误");
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setCodeType(codeType);
        phoneCode.setPhone(phone);
        phoneCode.setBeUsed(YesOrNoEnum.N.getCode());
        phoneCode.setReqIp(WebUtils.getClientIp());
        phoneCode.setCreateBy(-1L);
        String sms_switch = this.aliyunSmsConfig.getSmsSwitch();
        logger.info(phone + "," + codeType + " sendSmsCode sms_switch=" + sms_switch);
        Date expireDate;
        if (StringUtils.equals(sms_switch, "true")) {
            expireDate = getDelayMinute(15);
            code = templateParam.get("code");
            this.aliyunSmsSender.send(phone, SmsTemplate, templateParam);
        } else {
            code = "1234";
            expireDate = getDelayMinute(15);
        }

        phoneCode.setExpiresDate(expireDate);
        phoneCode.setCode(code);
        save(phoneCode);
        phoneCodeDto.setExpiresDate(expireDate.getTime());
        return phoneCodeDto;
    }

    @Transactional(
            rollbackFor = {Exception.class, RuntimeException.class}
    )
    public PhoneCodeDto sendSmsCodeThirdParty(String phone, String codeType, String content, String code) {
        PhoneCodeDto phoneCodeDto = new PhoneCodeDto();
        phoneCodeDto.setExpiresTime(SMS_EXPIRES_TIME);
        Assert.notNull(PhoneCodeTypeEnum.getEnum(codeType), "验证码类型错误");
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setCodeType(codeType);
        phoneCode.setPhone(phone);
        phoneCode.setBeUsed(YesOrNoEnum.N.getCode());
        phoneCode.setReqIp(WebUtils.getClientIp());
        phoneCode.setCode(code);
        phoneCode.setCreateBy(-1L);
        String sms_switch = this.aliyunSmsConfig.getSmsSwitch();
        logger.info(phone + "," + codeType + " sendSmsCode sms_switch=" + sms_switch);
        Date expireDate;
        if (StringUtils.equals(sms_switch, "true")) {
            expireDate = getDelayMinute(15);
            smsClientSend.sendSms(phone, content);
        } else {
            code = "1234";
            expireDate = getDelayMinute(15);
        }

        phoneCode.setExpiresDate(expireDate);
        phoneCode.setCode(code);
        save(phoneCode);
        phoneCodeDto.setExpiresDate(expireDate.getTime());
        return phoneCodeDto;
    }

    @Override
    public void sendSms(String phone, String content) {
        smsClientSend.sendSms(phone, content);
    }
}

