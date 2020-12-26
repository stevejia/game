package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.service.distribute.game.enums.UsersLevelEnum;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import com.gongyu.snowcloud.framework.util.BeanUtils;
import com.google.common.base.Converter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;

@Builder
@Data
@ApiModel
public class UsersResponseDto extends BaseEntity {
    @ApiModelProperty(value = "表id", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "邀请码url", dataType = "String")
    private String inviteCode;
    @ApiModelProperty(value = "会员昵称", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "会员等级", dataType = "String")
    private String levelName;
    @ApiModelProperty(value = "总资产", dataType = "BigDecimal")
    private BigDecimal totalAssets;
    @ApiModelProperty(value = "合约收益", dataType = "BigDecimal")
    private BigDecimal contractualIncome;
    @ApiModelProperty(value = "上级ID", dataType = "String")
    private Integer superiorId;
    @ApiModelProperty(value = "一级下线数", dataType = "Integer")
    private Long oneLowerLevel;
    @ApiModelProperty(value = "二级下线数", dataType = "Integer")
    private Long twoLowerLevel;
    @ApiModelProperty(value = "三级下线数", dataType = "Integer")
    private Long threeLowerLevel;
    @ApiModelProperty(value = "手机号码", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "推广收益", dataType = "BigDecimal")
    private BigDecimal extensionAmount;
    @ApiModelProperty(value = "消费茶籽", dataType = "Integer")
    private Integer payPoints;
    @ApiModelProperty(value = "注册时间", dataType = "Integer")
    private Integer regTime;
    @ApiModelProperty(value = "是否冻结 0 否 1 是", dataType = "Integer")
    private Integer isLock;
    @ApiModelProperty(value = "银行卡信息", dataType = "Integer")
    private String bankNumber;
    @ApiModelProperty(value = "用户编号", dataType = "Integer")
    private Integer code;
    @ApiModelProperty(value = "可出售木材", dataType = "Integer")
    private Integer totalProducts;
    
    @ApiModelProperty(value = "当日抢购情况", dataType = "String")
    private String todayRub;
    
    @ApiModelProperty(value = "近7日抢购次数", dataType = "Integer")
    private Integer sevenDaysRub;
    
    @ApiModelProperty(value = "近3日抢购次数", dataType = "Integer")
    private Integer threeDaysRub;

    @Tolerate
    public UsersResponseDto() {
    }

    public UsersResponseDto convertToUser(Users users) {
        UserCopyDTOConvert userInputDTOConvert = new UserCopyDTOConvert();
        UsersResponseDto convert = userInputDTOConvert.convert(users);
        return convert;
    }

    public Users convertToUsersResponseDto() {
        UserCopyDTOConvert userDTOConvert = new UserCopyDTOConvert();
        Users users = userDTOConvert.reverse().convert(this);
        return users;
    }

    private static class UserCopyDTOConvert extends Converter<Users, UsersResponseDto> {
        @Override
        protected UsersResponseDto doForward(Users users) {
            UsersResponseDto usersResponseDto = UsersResponseDto.builder().build();
            BeanUtils.copyProperties(users, usersResponseDto);
            usersResponseDto.setOneLowerLevel(users.getFirstLeader());
            usersResponseDto.setThreeLowerLevel(users.getThirdLeader());
            usersResponseDto.setTwoLowerLevel(users.getSecondLeader());
            usersResponseDto.setLevelName(UsersLevelEnum.getEnum(users.getLevel()).getName());
            return usersResponseDto;
        }

        @Override
        protected Users doBackward(UsersResponseDto usersResponseDto) {
            Users users = new Users();
            BeanUtils.copyProperties(usersResponseDto, users);
            return users;
        }
    }

}