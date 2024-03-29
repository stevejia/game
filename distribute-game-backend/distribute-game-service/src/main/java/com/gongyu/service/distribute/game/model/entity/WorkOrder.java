package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_work_order")
@Data
@ApiModel
public class WorkOrder extends BaseDataEntity {
    @ApiModelProperty(value = "会员ID", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "积分", dataType = "String")
    private String contactInformation;
    @ApiModelProperty(value = "标题", dataType = "String")
    private String title;
    @ApiModelProperty(value = "概率", dataType = "String")
    private String image;
    @ApiModelProperty(value = "审核状态 1待审核 2通过 3不通过", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    @ApiModelProperty(value = "具体内容", dataType = "String")
    private String contentDetail;
}