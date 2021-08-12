package com.futures.common.enums;

import com.gongyu.snowcloud.framework.base.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 20:48
 */
@Getter
@AllArgsConstructor
public enum AppealStatusEnum {

    NOT_AUDIT(0,"未审核"),
    APPEAL_PASS(1,"申诉通过"),
    APPEAL_NO_PASS(2,"申诉不通过"),
    APPEAL_FAIL(3,"申诉失败"),
    ;


    Integer code;

    String desc;

    public static AppealStatusEnum parse(Integer code){
        return Arrays.stream(AppealStatusEnum.values())
                .filter(item -> item.code.intValue() == code.intValue())
                .findFirst()
                .orElseThrow(() -> new BizException("枚举解析失败 code：" + code));
    }
}
