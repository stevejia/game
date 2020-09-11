package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PictureModifyDto;
import com.gongyu.service.distribute.game.model.dto.PictureSaveDto;
import com.gongyu.service.distribute.game.model.entity.Picture;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface PictureService extends CrudService<Picture>{

    IPage<Picture> queryPicture(IPage<Picture> page);

    void savePicture(PictureSaveDto pictureSaveDto);

    void modifyPicture(PictureModifyDto pictureModifyDto);
}