package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.ArticleCateModifyDto;
import com.futures.model.dto.ArticleCateSaveDto;
import com.futures.model.entity.ArticleCate;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ArticleCateService extends CrudService<ArticleCate>{

    IPage<ArticleCate> queryArticleCate(IPage<ArticleCate> page);

    void saveArticleCate(ArticleCateSaveDto articleCateSaveDto);

    void modifyArticleCate(ArticleCateModifyDto articleCateModifyDto);
}