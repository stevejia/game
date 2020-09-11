package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.WorkOrderModifyDto;
import com.gongyu.service.distribute.game.model.dto.WorkOrderSaveDto;
import com.gongyu.service.distribute.game.model.entity.WorkOrder;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface WorkOrderService extends CrudService<WorkOrder> {

    IPage<WorkOrderSaveDto> queryWorkOrder(IPage<WorkOrderSaveDto> page, WorkOrderSaveDto workOrderSaveDto);

    void saveWorkOrder(WorkOrderSaveDto workOrderSaveDto);

    void modifyWorkOrder(WorkOrderModifyDto workOrderModifyDto);
}