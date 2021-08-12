package com.futures.model.dto;

import com.futures.model.entity.Article;
import com.futures.model.entity.PigGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 14:16
 */
@Data
public class HomeResultDto implements Serializable {

    @ApiModelProperty(value = "英雄列表",dataType = "PigGoods")
    private List<PigGoodsResultDto> goods;

    @ApiModelProperty(value = "首页公告",dataType = "Article")
    private List<Article> articles;
}
