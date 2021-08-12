package com.futures.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.model.dto.WorkOrderModifyDto;
import com.futures.model.dto.WorkOrderSaveDto;
import com.futures.model.entity.WorkOrder;
import com.futures.service.WorkOrderService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("workOrder")
@Api(tags = "工单管理")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @ApiOperation(value = "【工单管理】列表", notes = "【工单管理】列表", response = WorkOrder.class)
    @PostMapping("queryWorkOrder")
    public BaseResponse queryWorkOrder(Page page, @Valid @ModelAttribute WorkOrderSaveDto workOrderSaveDto) {
        return BaseResponse.success(workOrderService.queryWorkOrder(page, workOrderSaveDto));
    }

    @ApiOperation(value = "【工单管理】详情", notes = "【工单管理】详情", response = WorkOrder.class)
    @PostMapping("getWorkOrder")
    public BaseResponse getWorkOrder(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        return BaseResponse.success(workOrderService.getById(id));
    }

    @ApiOperation(value = "【工单管理】添加", notes = "【工单管理】添加")
    @PostMapping("saveWorkOrder")
    @SysUserLog(module = "工单管理", action = "添加")
    public BaseResponse saveWorkOrder(@Valid @ModelAttribute WorkOrderSaveDto workOrderSaveDto) {
        workOrderService.saveWorkOrder(workOrderSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【工单管理】修改", notes = "【工单管理】修改")
    @PostMapping("modifyWorkOrder")
    @SysUserLog(module = "工单管理", action = "修改")
    public BaseResponse modifyWorkOrder(@Valid @ModelAttribute WorkOrderModifyDto workOrderModifyDto) {
        workOrderService.modifyWorkOrder(workOrderModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【工单管理】删除", notes = "【工单管理】删除")
    @PostMapping("deleteWorkOrder")
    @SysUserLog(module = "工单管理", action = "删除")
    public BaseResponse deleteWorkOrder(@ApiParam(value = "id", required = true) @RequestParam() Long id) {
        workOrderService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}