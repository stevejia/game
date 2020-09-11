package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.ExpenseLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.ExpenseLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.ExpenseLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ExpenseLogService extends CrudService<ExpenseLog>{

    IPage<ExpenseLog> queryExpenseLog(IPage<ExpenseLog> page);

    void saveExpenseLog(ExpenseLogSaveDto expenseLogSaveDto);

    void modifyExpenseLog(ExpenseLogModifyDto expenseLogModifyDto);
}