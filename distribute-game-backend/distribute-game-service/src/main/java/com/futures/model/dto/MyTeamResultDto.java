package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/1 17:10
 */
@Data
public class MyTeamResultDto {

    @ApiModelProperty(value = "直推用户",dataType = "MyTeamResultDto.User")
    private List<User> users;

    @ApiModelProperty(value = "团队用户数",dataType = "MyTeamResultDto.TeamUserNum")
    private TeamUserNum userNum;

    @Data
    public static class User{

        @ApiModelProperty(value = "昵称",dataType = "String")
        private String nickName;

        @ApiModelProperty(value = "用户ID",dataType = "Long")
        private Long userId;
    }

    @Data
    @AllArgsConstructor
    public static class TeamUserNum{

        @ApiModelProperty(value = "一代会员有效用户数",dataType = "Integer")
        private Integer firstValidNum;

        @ApiModelProperty(value = "一代会员无效用户数",dataType = "Integer")
        private Integer firstInvalidNum;

        @ApiModelProperty(value = "二代会员有效用户数",dataType = "Integer")
        private Integer twoValidNum;

        @ApiModelProperty(value = "二代会员无效用户数",dataType = "Integer")
        private Integer twoInvalidNum;

        @ApiModelProperty(value = "三代会员有效用户数",dataType = "Integer")
        private Integer threeValidNum;

        @ApiModelProperty(value = "三代会员无效用户数",dataType = "Integer")
        private Integer threeInvalidNum;

        public TeamUserNum() {
        }
    }
}
