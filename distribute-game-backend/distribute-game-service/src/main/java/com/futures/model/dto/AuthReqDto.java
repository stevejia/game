package com.futures.model.dto;

import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 17:02
 */
@Data
public class AuthReqDto {

    @ApiModelProperty(value = "真实姓名",dataType = "String")
    @NotBlank(message = "真实姓名不能为空")
    private String propleName;

    @ApiModelProperty(value = "身份证号码",dataType = "String")
    @NotBlank(message = "身份证号码不能为空")
    private String idCard;

    @ApiModelProperty(value = "验证码key",dataType = "String")
    @NotBlank(message = "验证码key不能为空")
    private String codeKey;

    @ApiModelProperty(value = "验证码",dataType = "String")
    @NotBlank(message = "验证码code不能为空")
    private String verifCode;


    /**
     * 验证码校验
     * @return
     */
    public String checkVerfCode(){
        String codeRedis = RedisUtils.get(codeKey);
        if (StringUtils.isEmpty(codeRedis)) {
            return "验证码失效";
        }
        if (!StringUtils.equals(verifCode, codeRedis)) {
            return "验证码错误";
        }
        RedisUtils.remove(codeKey);
        return StringUtils.EMPTY;
    }
}
