package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.ArticleCateMapper;
import com.gongyu.service.distribute.game.model.dto.ArticleCateModifyDto;
import com.gongyu.service.distribute.game.model.dto.ArticleCateSaveDto;
import com.gongyu.service.distribute.game.model.entity.ArticleCate;
import com.gongyu.service.distribute.game.service.ArticleCateService;
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