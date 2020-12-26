package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.snowcloud.framework.data.mybatis.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class WorkOrderSaveDto extends BaseDataEntity {
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Long userId;
    @ApiModelProperty(value = "茶籽", dataType = "String")
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
    @ApiModelProperty(value = "手机号", dataType = "String")
    private String mobile;
    @ApiModelProperty(value = "开始时间")
    private String startDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    @ApiModelProperty(value = "联系方式",dataType = "String")
    private String phone;
}