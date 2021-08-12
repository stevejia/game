package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PictureModifyDto;
import com.futures.model.dto.PictureSaveDto;
import com.futures.model.entity.Picture;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PictureService extends CrudService<Picture>{

    IPage<Picture> queryPicture(IPage<Picture> page);

    void savePicture(PictureSaveDto pictureSaveDto);

    void modifyPicture(PictureModifyDto pictureModifyDto);
}