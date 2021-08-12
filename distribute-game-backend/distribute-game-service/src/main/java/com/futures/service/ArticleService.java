package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.ArticleModifyDto;
import com.futures.model.dto.ArticleQueryDto;
import com.futures.model.dto.ArticleSaveDto;
import com.futures.model.entity.Article;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ArticleService extends CrudService<Article> {

    IPage<Article> queryArticle(IPage<Article> page, ArticleQueryDto articleSaveDto);

    void saveArticle(ArticleSaveDto articleSaveDto);

    void modifyArticle(ArticleModifyDto articleModifyDto);
}