package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.ArticleCateModifyDto;
import com.futures.model.dto.ArticleCateSaveDto;
import com.futures.model.entity.ArticleCate;
import com.futures.service.ArticleCateService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("articleCate")
@Api(tags = "文章分类管理")
public class ArticleCateController {

    @Autowired
    private ArticleCateService articleCateService;

    @ApiOperation(value = "【文章分类管理】列表", notes = "【文章分类管理】列表", response = ArticleCate.class)
    @PostMapping("queryArticleCate")
    public BaseResponse queryArticleCate(Page page) {
        return BaseResponse.success(articleCateService.queryArticleCate(page));
    }

    @ApiOperation(value = "【文章分类管理】详情", notes = "【文章分类管理】详情", response = ArticleCate.class)
    @PostMapping("getArticleCate")
    public BaseResponse getArticleCate(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(articleCateService.getById(id));
    }

    @ApiOperation(value = "【文章分类管理】添加", notes = "【文章分类管理】添加")
    @PostMapping("saveArticleCate")
    @SysUserLog(module = "文章分类管理", action = "添加")
    public BaseResponse saveArticleCate(@Valid @ModelAttribute ArticleCateSaveDto articleCateSaveDto) {
        articleCateService.saveArticleCate(articleCateSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【文章分类管理】修改", notes = "【文章分类管理】修改")
    @PostMapping("modifyArticleCate")
    @SysUserLog(module = "文章分类管理", action = "修改")
    public BaseResponse modifyArticleCate(@Valid @ModelAttribute ArticleCateModifyDto articleCateModifyDto) {
        articleCateService.modifyArticleCate(articleCateModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【文章分类管理】删除", notes = "【文章分类管理】删除")
    @PostMapping("deleteArticleCate")
    @SysUserLog(module = "文章分类管理", action = "删除")
    public BaseResponse deleteArticleCate(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        articleCateService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}