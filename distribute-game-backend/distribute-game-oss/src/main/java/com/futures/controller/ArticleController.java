package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.ArticleModifyDto;
import com.futures.model.dto.ArticleQueryDto;
import com.futures.model.dto.ArticleSaveDto;
import com.futures.model.entity.Article;
import com.futures.service.ArticleService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    public BaseResponse queryArticle(Page page, ArticleQueryDto articleQueryDto) {
        return BaseResponse.success(articleService.queryArticle(page, articleQueryDto));
    }

    @ApiOperation(value = "【文章管理】详情", notes = "【文章管理】详情", response = Article.class)
    @PostMapping("getArticle")
    public BaseResponse getArticle(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(articleService.getById(id));
    }

    @ApiOperation(value = "【文章管理】添加", notes = "【文章管理】添加")
    @PostMapping("saveArticle")
    @SysUserLog(module = "文章管理", action = "添加")
    public BaseResponse saveArticle(@Valid @ModelAttribute ArticleSaveDto articleSaveDto) {
        articleService.saveArticle(articleSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【文章管理】修改", notes = "【文章管理】修改")
    @PostMapping("modifyArticle")
    @SysUserLog(module = "文章管理", action = "修改")
    public BaseResponse modifyArticle(@Valid @ModelAttribute ArticleModifyDto articleModifyDto) {
        if(null == articleModifyDto.getId()){
            ArticleSaveDto articleSaveDto = new ArticleSaveDto();
            BeanUtils.copyProperties(articleModifyDto,articleSaveDto);
            if(StringUtils.isEmpty(articleSaveDto.getTitle())){
                articleSaveDto.setTitle(StringUtils.SPACE);
            }
            articleService.saveArticle(articleSaveDto);
        }else{
            articleService.modifyArticle(articleModifyDto);
        }
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【文章管理】删除", notes = "【文章管理】删除")
    @PostMapping("deleteArticle")
    @SysUserLog(module = "文章管理", action = "删除")
    public BaseResponse deleteArticle(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        articleService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}