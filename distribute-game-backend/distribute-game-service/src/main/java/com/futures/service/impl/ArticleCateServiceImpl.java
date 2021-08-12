package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.ArticleCateMapper;
import com.futures.model.dto.ArticleCateModifyDto;
import com.futures.model.dto.ArticleCateSaveDto;
import com.futures.model.entity.ArticleCate;
import com.futures.service.ArticleCateService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleCateServiceImpl extends CrudServiceSupport<ArticleCateMapper, ArticleCate> implements ArticleCateService  {

    @Override
    public IPage<ArticleCate> queryArticleCate(IPage<ArticleCate> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveArticleCate(ArticleCateSaveDto articleCateSaveDto) {
        ArticleCate articleCate = new ArticleCate();
        BeanUtils.copyProperties(articleCateSaveDto, articleCate);
        articleCate.setCreate_time(DateUtil.getNowDate());
        articleCate.setUpdate_time(DateUtil.getNowDate());
        this.save(articleCate);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyArticleCate(ArticleCateModifyDto articleCateModifyDto) {
        ArticleCate articleCate = new ArticleCate();
        BeanUtils.copyProperties(articleCateModifyDto, articleCate);
        articleCate.setUpdate_time(DateUtil.getNowDate());
        this.updateById(articleCate);
    }
}