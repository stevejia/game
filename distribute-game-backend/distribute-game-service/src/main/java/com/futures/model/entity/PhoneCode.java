package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("phone_code")
@Getter
@Setter
public class PhoneCode extends BaseDataEntity {

    /**
     * 验证码
     */
    private String code;
    /**
     * 验证码类型（REGISTER：注册 LOGIN：登录 FG_PWD：忘记密码）
     */
    private String codeType;
    /**
     * 有效时间
     */
    private Date expiresDate;
    /**
     * 是否被使用（Y：使用 N：未使用）
     */
    private String beUsed;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 获取验证码的ip
     */
    private String reqIp;

}
