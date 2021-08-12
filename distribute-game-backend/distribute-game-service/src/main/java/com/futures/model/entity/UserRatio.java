package com.futures.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_user_ratio")
@Data
@ApiModel
public class UserRatio extends BaseEntity {
    @ApiModelProperty(value = "满足的最低金额", dataType = "Integer")
    private Integer lowermoney;
    @ApiModelProperty(value = "满足的最高金额", dataType = "Integer")
    private Integer topmoney;
    @ApiModelProperty(value = "百分比", dataType = "Integer")
    private Integer proportion;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Integer createTime;
    @ApiModelProperty(value = "用户等级", dataType = "Integer")
    private Integer userLevel;
}