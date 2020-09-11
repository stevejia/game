package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.ArticleQueryDto;
import com.gongyu.service.distribute.game.model.entity.Article;
import com.gongyu.service.distribute.game.service.ArticleService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("article")
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "【文章管理】列表", notes = "【文章管理】列表", response = Article.class)
    @PostMapping("queryArticle")
    public BaseResponse queryArticle(Page page, @Valid @ModelAttribute ArticleQueryDto articleQueryDto) {
        IPage iPage = articleService.queryArticle(page, articleQueryDto);
        return BaseResponse.success(iPage);
    }

    @ApiOperation(value = "【文章管理】详情", notes = "【文章管理】详情", response = Article.class)
    @PostMapping("getArticle")
    public BaseResponse getArticle(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(articleService.getById(id));
    }

    @ApiOperation(value = "【积分描述】详情", notes = "【积分描述】详情", response = Article.class)
    @PostMapping("getPointDesc")
    public BaseResponse getPointDesc(@ApiParam(value = "cateId") @RequestParam Long cateId){
        Article article = articleService.getOne(new QueryWrapper<Article>().eq("cate_id", cateId));
        if(null == article){
            return BaseResponse.error("暂无描述");
        }
        return BaseResponse.success(article);
    }
}