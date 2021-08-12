package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.WorkOrderMapper;
import com.futures.model.dto.WorkOrderModifyDto;
import com.futures.model.dto.WorkOrderSaveDto;
import com.futures.model.entity.Users;
import com.futures.model.entity.WorkOrder;
import com.futures.service.UsersService;
import com.futures.service.WorkOrderService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkOrderServiceImpl extends CrudServiceSupport<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;
    @Autowired
    private UsersService usersService;

    @Override
    public IPage<WorkOrderSaveDto> queryWorkOrder(IPage<WorkOrderSaveDto> page, WorkOrderSaveDto workOrderSaveDto) {
        List<WorkOrderSaveDto> list = workOrderMapper.queryWorkOrder(page, workOrderSaveDto);
        for (WorkOrderSaveDto item : list) {
            Users users = usersService.getById(item.getUserId());
            item.setPhone(users.getMobile());
        }
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveWorkOrder(WorkOrderSaveDto workOrderSaveDto) {
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderSaveDto, workOrder);
        workOrder.setCreateBy(WebUtils.getCurrentUserId());
        workOrder.setUserId(workOrderSaveDto.getUserId().longValue());
        workOrder.setStatus(1);
        this.save(workOrder);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyWorkOrder(WorkOrderModifyDto workOrderModifyDto) {
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderModifyDto, workOrder);
        workOrder.setUpdateBy(WebUtils.getCurrentUserId());
        this.updateById(workOrder);
    }
}