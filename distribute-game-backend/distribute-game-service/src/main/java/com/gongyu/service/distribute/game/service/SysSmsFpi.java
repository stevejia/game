package com.gongyu.service.distribute.game.service;

import com.gongyu.service.distribute.game.model.dto.PhoneCodeDto;
import com.gongyu.service.distribute.game.model.entity.PhoneCode;
import com.gongyu.snowcloud.framework.base.exception.BizException;

import java.util.Map;

public interface SysSmsFpi {
    Long SMS_EXPIRES_TIME = 60L;
    int SMS_VALID_MINUTES = 15;

    boolean checkSmsCode(String phone, String code, String codeType) throws BizException;

    PhoneCode checkSmsCodeForNext(String phone, String code, String codeType) throws BizException;

    String getCode(Integer num) throws BizException;

    PhoneCodeDto sendSmsCode(String phone, String codeType, String SmsTemplate, Map<String, String> templateParam);

    PhoneCodeDto sendSmsCodeThirdParty(String phone, String codeType, String content, String code);

    void sendSms(String phone, String content);

}
