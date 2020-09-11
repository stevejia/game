package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.ArticleModifyDto;
import com.gongyu.service.distribute.game.model.dto.ArticleQueryDto;
import com.gongyu.service.distribute.game.model.dto.ArticleSaveDto;
import com.gongyu.service.distribute.game.model.entity.Article;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ArticleService extends CrudService<Article> {

    IPage<Article> queryArticle(IPage<Article> page, ArticleQueryDto articleSaveDto);

    void saveArticle(ArticleSaveDto articleSaveDto);

    void modifyArticle(ArticleModifyDto articleModifyDto);
}