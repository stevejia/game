package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.PictureMapper;
import com.futures.model.dto.PictureModifyDto;
import com.futures.model.dto.PictureSaveDto;
import com.futures.model.entity.Picture;
import com.futures.service.PictureService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureServiceImpl extends CrudServiceSupport<PictureMapper, Picture> implements PictureService  {

    @Override
    public IPage<Picture> queryPicture(IPage<Picture> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void savePicture(PictureSaveDto pictureSaveDto) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureSaveDto, picture);
        this.save(picture);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyPicture(PictureModifyDto pictureModifyDto) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureModifyDto, picture);
        this.updateById(picture);
    }
}