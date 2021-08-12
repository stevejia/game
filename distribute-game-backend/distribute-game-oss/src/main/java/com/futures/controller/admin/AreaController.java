package com.futures.controller.admin;

import com.futures.model.dto.SysAreaStructuredDto;
import com.futures.model.dto.SysAreaUnstructuredDto;
import com.futures.service.SysAreaFpi;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("area")
@Api(tags = "区域查询")
@Slf4j
public class AreaController {
    @Autowired
    private SysAreaFpi sysAreaFpi;

    @ApiOperation(value = "获取所有区域（非结构化）", notes = "获取所有区域（非结构化）",response = SysAreaUnstructuredDto.class)
    @PostMapping("queryAreaUnStructured")
    public BaseResponse queryAreaUnStructured() {
        return BaseResponse.success(sysAreaFpi.queryAreaUnStructured());
    }

    @ApiOperation(value = "获取所有区域（结构化）", notes = "获取所有区域（结构化）",response = SysAreaStructuredDto.class)
    @PostMapping("queryAreaStructured")
    public BaseResponse queryAreaStructured() {
        return BaseResponse.success(sysAreaFpi.queryAreaStructured());
    }

    @ApiOperation(value = "根据父ID查询子区域", notes = "根据父ID查询子区域",response = SysAreaUnstructuredDto.class)
    @PostMapping("queryAreaByParentId")
    public BaseResponse wqueryAreaByParentId(@ApiParam(value = "parentId", required = true) @RequestParam()Long parentId) {
        return BaseResponse.success(sysAreaFpi.queryAreaByParentId(parentId));
    }
}
