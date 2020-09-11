package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.ConfigModifyDto;
import com.gongyu.service.distribute.game.model.dto.ConfigSaveDto;
import com.gongyu.service.distribute.game.model.entity.Config;
import com.gongyu.service.distribute.game.service.ConfigService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("config")
@Api(tags = "配置管理")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ApiOperation(value = "【配置管理】列表", notes = "【配置管理】列表", response = Config.class)
    @PostMapping("queryConfig")
    public BaseResponse queryConfig(Page page,
                                    @ApiParam(value = "configGroup 配置分组 2=收款设置 4=规则配置 6=分销设置 7=下载设置", required = true) @RequestParam() Integer configGroup) {
        return BaseResponse.success(configService.queryConfig(page, configGroup));
    }

    @ApiOperation(value = "【配置管理】详情", notes = "【配置管理】详情", response = Config.class)
    @PostMapping("getConfig")
    public BaseResponse getConfig(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(configService.getById(id));
    }

    @ApiOperation(value = "【配置管理】添加", notes = "【配置管理】添加")
    @PostMapping("saveConfig")
    @SysUserLog(module = "配置管理", action = "添加")
    public BaseResponse saveConfig(@Valid @ModelAttribute ConfigSaveDto configSaveDto) {
        configService.saveConfig(configSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【配置管理】修改", notes = "【配置管理】修改")
    @PostMapping("modifyConfig")
    @SysUserLog(module = "配置管理", action = "修改")
    public BaseResponse modifyConfig(@Valid @ModelAttribute ConfigModifyDto configModifyDto) {
        configService.modifyConfig(configModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【配置管理】删除", notes = "【配置管理】删除")
    @PostMapping("deleteConfig")
    @SysUserLog(module = "配置管理", action = "删除")
    public BaseResponse deleteConfig(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        configService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}