package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.ArticleCateModifyDto;
import com.gongyu.service.distribute.game.model.dto.ArticleCateSaveDto;
import com.gongyu.service.distribute.game.model.entity.ArticleCate;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ArticleCateService extends CrudService<ArticleCate>{

    IPage<ArticleCate> queryArticleCate(IPage<ArticleCate> page);

    void saveArticleCate(ArticleCateSaveDto articleCateSaveDto);

    void modifyArticleCate(ArticleCateModifyDto articleCateModifyDto);
}