package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.mapper.ExpenseLogMapper;
import com.gongyu.service.distribute.game.model.dto.ExpenseLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.ExpenseLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.ExpenseLog;
import com.gongyu.service.distribute.game.service.ExpenseLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseLogServiceImpl extends CrudServiceSupport<ExpenseLogMapper, ExpenseLog> implements ExpenseLogService  {

    @Override
    public IPage<ExpenseLog> queryExpenseLog(IPage<ExpenseLog> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveExpenseLog(ExpenseLogSaveDto expenseLogSaveDto) {
        ExpenseLog expenseLog = new ExpenseLog();
        BeanUtils.copyProperties(expenseLogSaveDto, expenseLog);
        this.save(expenseLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyExpenseLog(ExpenseLogModifyDto expenseLogModifyDto) {
        ExpenseLog expenseLog = new ExpenseLog();
        BeanUtils.copyProperties(expenseLogModifyDto, expenseLog);
        this.updateById(expenseLog);
    }
}