package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.ArticleMapper;
import com.gongyu.service.distribute.game.model.dto.ArticleModifyDto;
import com.gongyu.service.distribute.game.model.dto.ArticleQueryDto;
import com.gongyu.service.distribute.game.model.dto.ArticleSaveDto;
import com.gongyu.service.distribute.game.model.entity.Article;
import com.gongyu.service.distribute.game.service.ArticleService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.data.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl extends CrudServiceSupport<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> queryArticle(IPage<Article> page, ArticleQueryDto articleQueryDto) {
        QueryWrapper<Article> cateId = new QueryWrapper<Article>().eq("cate_id", articleQueryDto.getCateId());
        IPage<Article> page1 = this.page(page, cateId);
        return page1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveArticle(ArticleSaveDto articleSaveDto) {
        Article article = new Article();
        BeanUtils.copyProperties(articleSaveDto, article);
        article.setCreateTime(DateUtil.getNowDate());
        article.setUpdateTime(DateUtil.getNowDate());
        this.save(article);
        this.cleanCache();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyArticle(ArticleModifyDto articleModifyDto) {
        Article article = new Article();
        BeanUtils.copyProperties(articleModifyDto, article);
        article.setUpdateTime(DateUtil.getNowDate());
        this.updateById(article);
        this.cleanCache();
    }

    public void cleanCache() {
        Object obj = RedisUtils.get("home_article");
        if (null != obj) {
            RedisUtils.remove("home_article");
        }
    }
}