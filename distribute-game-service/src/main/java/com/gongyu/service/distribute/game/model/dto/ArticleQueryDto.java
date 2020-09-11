package com.gongyu.service.distribute.game.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ArticleQueryDto {
    @ApiModelProperty(value = "文章类别 1帮助中心 2系统消息 3活动通知 4线上客服（客服中心） 5首页公告 6最新公告 7问题分类", dataType = "Integer")
    private Integer cateId;

}