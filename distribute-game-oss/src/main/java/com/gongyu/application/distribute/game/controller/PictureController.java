package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PictureModifyDto;
import com.gongyu.service.distribute.game.model.dto.PictureSaveDto;
import com.gongyu.service.distribute.game.model.entity.Picture;
import com.gongyu.service.distribute.game.service.PictureService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("picture")
@Api(tags = "图片管理")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "【图片管理】列表", notes = "【图片管理】列表", response = Picture.class)
    @PostMapping("queryPicture")
    public BaseResponse queryPicture(Page page) {
        return BaseResponse.success(pictureService.queryPicture(page));
    }

    @ApiOperation(value = "【图片管理】详情", notes = "【图片管理】详情", response = Picture.class)
    @PostMapping("getPicture")
    public BaseResponse getPicture(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pictureService.getById(id));
    }

    @ApiOperation(value = "【图片管理】添加", notes = "【图片管理】添加")
    @PostMapping("savePicture")
    @SysUserLog(module = "图片管理", action = "添加")
    public BaseResponse savePicture(@Valid @ModelAttribute PictureSaveDto pictureSaveDto) {
        pictureService.savePicture(pictureSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【图片管理】修改", notes = "【图片管理】修改")
    @PostMapping("modifyPicture")
    @SysUserLog(module = "图片管理", action = "修改")
    public BaseResponse modifyPicture(@Valid @ModelAttribute PictureModifyDto pictureModifyDto) {
        pictureService.modifyPicture(pictureModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【图片管理】删除", notes = "【图片管理】删除")
    @PostMapping("deletePicture")
    @SysUserLog(module = "图片管理", action = "删除")
    public BaseResponse deletePicture(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pictureService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}