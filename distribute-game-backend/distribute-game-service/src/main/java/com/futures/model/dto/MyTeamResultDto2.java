package com.futures.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author steve.jia
 * @version 1.0
 * @date 2020/10/13 19:40
 */
@Data
public class MyTeamResultDto2 {

    @ApiModelProperty(value = "一级推广用户",dataType = "MyTeamResultDto.User")
    private List<User> firstUsers;
    
    @ApiModelProperty(value = "一级推广用户",dataType = "MyTeamResultDto.User")
    private List<User> secondUsers;
    
    @ApiModelProperty(value = "一级推广用户",dataType = "MyTeamResultDto.User")
    private List<User> thirdUsers;

    @Data
    public static class User{

        @ApiModelProperty(value = "昵称",dataType = "String")
        private String nickName;
        
        @ApiModelProperty(value = "手机",dataType = "String")
        private String mobile;

        @ApiModelProperty(value = "用户ID",dataType = "Long")
        private Long userId;
    }
}
