package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.FileModifyDto;
import com.gongyu.service.distribute.game.model.dto.FileSaveDto;
import com.gongyu.service.distribute.game.model.entity.File;
import com.gongyu.service.distribute.game.service.FileService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("file")
@Api(tags = "文件管理")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "【文件管理】列表", notes = "【文件管理】列表", response = File.class)
    @PostMapping("queryFile")
    public BaseResponse queryFile(Page page) {
        return BaseResponse.success(fileService.queryFile(page));
    }

    @ApiOperation(value = "【文件管理】详情", notes = "【文件管理】详情", response = File.class)
    @PostMapping("getFile")
    public BaseResponse getFile(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(fileService.getById(id));
    }

    @ApiOperation(value = "【文件管理】添加", notes = "【文件管理】添加")
    @PostMapping("saveFile")
    @SysUserLog(module = "文件管理", action = "添加")
    public BaseResponse saveFile(@Valid @ModelAttribute FileSaveDto fileSaveDto) {
        fileService.saveFile(fileSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【文件管理】修改", notes = "【文件管理】修改")
    @PostMapping("modifyFile")
    @SysUserLog(module = "文件管理", action = "修改")
    public BaseResponse modifyFile(@Valid @ModelAttribute FileModifyDto fileModifyDto) {
        fileService.modifyFile(fileModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【文件管理】删除", notes = "【文件管理】删除")
    @PostMapping("deleteFile")
    @SysUserLog(module = "文件管理", action = "删除")
    public BaseResponse deleteFile(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        fileService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}