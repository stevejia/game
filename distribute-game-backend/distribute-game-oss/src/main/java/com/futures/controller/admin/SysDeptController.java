package com.futures.controller.admin;

import com.futures.model.dto.SysDeptDto;
import com.futures.model.entity.SysDept;
import com.futures.service.SysDeptFpi;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/dept")
@Api(tags = "系统管理")
@Slf4j
public class SysDeptController {
    @Autowired
    private SysDeptFpi sysDeptFpi;

    @ApiOperation(value = "【部门管理】-部门列表", notes = "部门管理-部门列表")
    @PostMapping(value = "queryDept")
    public BaseResponse queryDept(Pageable page, @ApiParam(value = "部门名称") String deptName) {
        try {
            return BaseResponse.success(sysDeptFpi.queryDept(page.getPage(), deptName));
        } catch (Exception e) {
            log.error("queryDept error=" + e.getMessage(), e);
            return BaseResponse.error("查询部门列表失败");
        }
    }

    @ApiOperation(value = "【人员管理】-添加部门", notes = "部门管理-添加部门")
    @PostMapping(value = "addDept")
    @SysUserLog(module = "部门管理", action = "添加部门")
    public BaseResponse addDept(SysDeptDto sysDeptDto) {
        try {
            SysDept sysDept = new SysDept();
            BeanUtils.copyProperties(sysDeptDto, sysDept);
            sysDeptFpi.saveDept(sysDept);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("addDept error=" + e.getMessage(), e);
            return BaseResponse.error("添加部门失败");
        }
    }

    @ApiOperation(value = "【部门管理】-修改部门", notes = "部门管理-修改部门")
    @PostMapping(value = "modifyDept")
    @SysUserLog(module = "部门管理", action = "修改部门")
    public BaseResponse modifyDept(SysDeptDto sysDeptDto) {
        if (null == sysDeptDto.getId()) {
            return BaseResponse.error("参数错误");
        }
        try {
            SysDept sysDept = new SysDept();
            BeanUtils.copyProperties(sysDeptDto, sysDept);
            sysDeptFpi.modifyDept(sysDept);
            return BaseResponse.success();
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("editDept error=" + e.getMessage(), e);
            return BaseResponse.error("编辑部门失败");
        }
    }

    @ApiOperation(value = "【部门管理】-删除部门", notes = "部门管理-删除部门")
    @PostMapping(value = "deleteDept")
    @SysUserLog(module = "部门管理", action = "删除部门")
    public BaseResponse deleteDept(@ApiParam(value = "部门ID",required = true)Long id) {
        if (null==id) {
            return BaseResponse.error("请选择要删除的部门");
        }
        try {
            return BaseResponse.success(sysDeptFpi.deleteDept(id));
        } catch (BizException e) {
            return BaseResponse.error(e.getMessage());
        } catch (Exception e) {
            log.error("deleteDept error=" + e.getMessage(), e);
            return BaseResponse.error("删除部门失败");
        }
    }
}
