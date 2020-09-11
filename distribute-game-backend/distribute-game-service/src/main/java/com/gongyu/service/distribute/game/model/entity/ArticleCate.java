package com.gongyu.service.distribute.game.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("zp_article_cate")
@Data
@ApiModel
public class ArticleCate extends BaseEntity {
    @ApiModelProperty(value = "分类名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "排序", dataType = "String")
    private String orderby;
    @ApiModelProperty(value = "封面url", dataType = "String")
    private String cover_url;
    @ApiModelProperty(value = "图标url", dataType = "String")
    private String icon_url;
    @ApiModelProperty(value = "创建时间", dataType = "Integer")
    private Long create_time;
    @ApiModelProperty(value = "更新时间", dataType = "Integer")
    private Long update_time;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer status;
}