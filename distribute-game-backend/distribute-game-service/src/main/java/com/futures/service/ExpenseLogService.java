package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.ExpenseLogModifyDto;
import com.futures.model.dto.ExpenseLogSaveDto;
import com.futures.model.entity.ExpenseLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface ExpenseLogService extends CrudService<ExpenseLog>{

    IPage<ExpenseLog> queryExpenseLog(IPage<ExpenseLog> page);

    void saveExpenseLog(ExpenseLogSaveDto expenseLogSaveDto);

    void modifyExpenseLog(ExpenseLogModifyDto expenseLogModifyDto);
}