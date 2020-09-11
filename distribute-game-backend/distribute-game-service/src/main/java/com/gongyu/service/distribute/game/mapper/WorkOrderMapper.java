package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.WorkOrderSaveDto;
import com.gongyu.service.distribute.game.model.entity.WorkOrder;
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