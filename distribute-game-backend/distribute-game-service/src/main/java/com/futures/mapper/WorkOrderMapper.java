package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.WorkOrderSaveDto;
import com.futures.model.entity.WorkOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    /**
     * 查询工单
     *
     * @param page
     * @return
     */
    List<WorkOrderSaveDto> queryWorkOrder(IPage<WorkOrderSaveDto> page, @Param("dto") WorkOrderSaveDto workOrderSaveDto);
}