package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.WorkOrderModifyDto;
import com.futures.model.dto.WorkOrderSaveDto;
import com.futures.model.entity.WorkOrder;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface WorkOrderService extends CrudService<WorkOrder> {

    IPage<WorkOrderSaveDto> queryWorkOrder(IPage<WorkOrderSaveDto> page, WorkOrderSaveDto workOrderSaveDto);

    void saveWorkOrder(WorkOrderSaveDto workOrderSaveDto);

    void modifyWorkOrder(WorkOrderModifyDto workOrderModifyDto);
}