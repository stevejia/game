package com.gongyu.application.distribute.game.controller.admin;

import com.gongyu.service.distribute.game.model.entity.SysConfig;
import com.gongyu.service.distribute.game.service.SysConfigFpi;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/config")
@Api(tags = "系统管理")
public class SysConfigController {
    @Autowired
    private SysConfigFpi sysConfigFpi;

    @ApiOperation(value = "【系统配置】-查询单个配置", notes = "系统配置-查询单个配置",response = SysConfig.class)
    @PostMapping(value = "getSysConfig")
    public BaseResponse getSysConfig(@ApiParam(value = "配置键") @RequestParam String configKey) {
        return BaseResponse.success(sysConfigFpi.getSysConfig(configKey));
    }

    @ApiOperation(value = "【系统配置】-系统配置列表", notes = "系统配置-系统配置列表",response = SysConfig.class)
    @PostMapping(value = "querySysConfig")
    public BaseResponse querySysConfig(Pageable page, @ApiParam(value = "配置键") String configKey) {
        return BaseResponse.success(sysConfigFpi.querySysConfig(page.getPage(), configKey));
    }

    @ApiOperation(value = "【系统配置】-添加系统配置", notes = "系统配置-添加系统配置")
    @PostMapping(value = "saveSysConfig")
    @SysUserLog(module = "系统配置", action = "添加系统配置")
    public BaseResponse saveSysConfig(@ApiParam(value = "配置键", required = true) @RequestParam() String configKey,
                                      @ApiParam(value = "配置值", required = true) @RequestParam() String configValue,
                                      @ApiParam(value = "备注", required = true) @RequestParam() String remark) {
        if(StringUtils.isEmpty(configKey)){
            return BaseResponse.paramError("配置键不能为空");
        }
        if(StringUtils.isEmpty(configValue)){
            return BaseResponse.paramError("配置值不能为空");
        }
        if(StringUtils.isEmpty(remark)){
            return BaseResponse.paramError("备注不能为空");
        }
        sysConfigFpi.saveSysConfig(configKey, configValue, remark);
        return BaseResponse.success();
    }

    @ApiOperation(value = "【系统配置】-修改系统配置", notes = "系统配置-修改系统配置")
    @PostMapping(value = "modifySysConfig")
    @SysUserLog(module = "系统配置", action = "修改系统配置")
    public BaseResponse modifySysConfig(@ApiParam(value = "配置键ID", required = true) @RequestParam() Long id,
                                        @ApiParam(value = "配置键", required = true) @RequestParam() String configKey,
                                        @ApiParam(value = "配置值", required = true) @RequestParam() String configValue,
                                        @ApiParam(value = "备注", required = true) @RequestParam() String remark) {
        if(null==id){
            return BaseResponse.paramError("配置键ID不能为空");
        }
        if(StringUtils.isEmpty(configKey)){
            return BaseResponse.paramError("配置键不能为空");
        }
        if(StringUtils.isEmpty(configValue)){
            return BaseResponse.paramError("配置值不能为空");
        }
        if(StringUtils.isEmpty(remark)){
            return BaseResponse.paramError("备注不能为空");
        }
        sysConfigFpi.modifySysConfig(id, configKey, configValue, remark);
        return BaseResponse.success();
    }

    @ApiOperation(value = "【系统配置】-删除系统配置", notes = "系统配置-删除系统配置")
    @PostMapping(value = "deleteSysConfig")
    @SysUserLog(module = "系统配置", action = "删除系统配置")
    public BaseResponse deleteSysConfig(@ApiParam(value = "配置键ID", required = true) @RequestParam() Long id) {
        sysConfigFpi.deleteSysConfig(id);
        return BaseResponse.success();
    }

}
