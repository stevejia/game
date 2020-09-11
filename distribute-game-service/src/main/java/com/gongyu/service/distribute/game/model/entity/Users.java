package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("zp_users")
@Data
@ApiModel
public class Users extends BaseEntity {
    @ApiModelProperty(value = "会员等级", dataType = "Integer")
    private Integer level;
    @ApiModelProperty(value = "第一个上级", dataType = "Integer")
    private Long firstLeader;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "头像", dataType = "String")
    private String headPic;
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;
    @ApiModelProperty(value = "支付密码", dataType = "String")
    private String paypwd;
    @ApiModelProperty(value = "0 保密 1 男 2 女", dataType = "Integer")
    private Integer sex;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "是否验证手机", dataType = "Integer")
    private Integer mobileValidated;
    @ApiModelProperty(value = "用户金额", dataType = "BigDecimal")
    private BigDecimal userMoney;
    @ApiModelProperty(value = "消费积分", dataType = "Integer")
    private Integer payPoints;
    @ApiModelProperty(value = "分佣金额", dataType = "BigDecimal")
    private BigDecimal distributMoney;
    @ApiModelProperty(value = "冻结金额", dataType = "BigDecimal")
    private BigDecimal frozenMoney;
    @ApiModelProperty(value = "第二个上级", dataType = "Integer")
    private Long secondLeader;
    @ApiModelProperty(value = "第三个上级", dataType = "Integer")
    private Long thirdLeader;
    @ApiModelProperty(value = "优惠码", dataType = "Integer")
    private Integer code;
    @ApiModelProperty(value = "用户下线总数", dataType = "Integer")
    private Integer underlingNumber;
    @ApiModelProperty(value = "默认收货地址", dataType = "Integer")
    private Integer addressId;
    @ApiModelProperty(value = "注册时间", dataType = "Integer")
    private Integer regTime;
    @ApiModelProperty(value = "最后登录时间", dataType = "Integer")
    private Long lastLoginTime;
    @ApiModelProperty(value = "最后登录ip", dataType = "String")
    private String lastLoginIp;
    @ApiModelProperty(value = "第三方来源 wx weibo alipay", dataType = "String")
    private String oauth;
    @ApiModelProperty(value = "第三方唯一标示", dataType = "String")
    private String openid;
    @ApiModelProperty(value = "", dataType = "String")
    private String unionid;
    @ApiModelProperty(value = "省份", dataType = "Integer")
    private Integer province;
    @ApiModelProperty(value = "市区", dataType = "Integer")
    private Integer city;
    @ApiModelProperty(value = "县", dataType = "Integer")
    private Integer district;
    @ApiModelProperty(value = "是否验证电子邮箱", dataType = "Integer")
    private Integer emailValidated;
    @ApiModelProperty(value = "会员折扣，默认1不享受", dataType = "BigDecimal")
    private BigDecimal discount;
    @ApiModelProperty(value = "消费累计额度", dataType = "BigDecimal")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "是否被锁定冻结", dataType = "Integer")
    private Integer isLock;
    @ApiModelProperty(value = "是否为分销商 0 否 1 是", dataType = "Integer")
    private Integer isDistribut;
    @ApiModelProperty(value = "用于app 授权类似于session_id", dataType = "String")
    private String token;
    @ApiModelProperty(value = "消息掩码", dataType = "Integer")
    private Integer messageMask;
    @ApiModelProperty(value = "推送id", dataType = "String")
    private String pushId;
    @ApiModelProperty(value = "分销商等级", dataType = "Integer")
    private Integer distributLevel;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "1:普通用户，2:微信授权用户,9:虚拟用户", dataType = "Integer")
    private Integer userType;
    @ApiModelProperty(value = "是否绑定微信 :1绑定", dataType = "Integer")
    private Integer isBindWeixin;
    @ApiModelProperty(value = "身份证", dataType = "String")
    private String identity;
    @ApiModelProperty(value = "真实姓名", dataType = "String")
    private String realName;
    @ApiModelProperty(value = "0禁止排单1以上就是正常排单", dataType = "Integer")
    private Integer ruleSort;
    @ApiModelProperty(value = "DOGE币", dataType = "Integer")
    private Integer dogeMoney;
    @ApiModelProperty(value = "PIG币", dataType = "Integer")
    private Integer pigCurrency;
    @ApiModelProperty(value = "累计收益", dataType = "BigDecimal")
    private BigDecimal accumulatedIncome;
    @ApiModelProperty(value = "给财分加盐", dataType = "String")
    private String usermoneysalt;
    @ApiModelProperty(value = "今天拥有的收益积分", dataType = "BigDecimal")
    private BigDecimal todayUsermoney;
    @ApiModelProperty(value = "今天认证失败多少次", dataType = "Integer")
    private Integer todayCheckIndentityNum;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer todayCheckIndentityDay;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer todayUsermoneyDay;
    @ApiModelProperty(value = "今天玩了多少次", dataType = "String")
    private String todayLuckturnNum;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer inviteId;
    @ApiModelProperty(value = "推荐收益", dataType = "BigDecimal")
    private BigDecimal recomIncome;

    @ApiModelProperty(value = "团队粉丝人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer oneLevel = 0;
    @ApiModelProperty(value = "团队正式用户人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer twoLevel = 0;
    @ApiModelProperty(value = "团队初级合伙人人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer threeLevel = 0;
    @ApiModelProperty(value = "团队中级合伙人人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer fourLevel = 0;
    @ApiModelProperty(value = "团队高级合伙人人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer fiveLevel = 0;
    @ApiModelProperty(value = "团队联合合伙人人数", dataType = "Integer")
    @TableField(exist = false)
    private Integer sixLevel = 0;


}