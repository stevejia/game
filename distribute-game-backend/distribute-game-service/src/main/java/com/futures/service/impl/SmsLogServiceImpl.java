package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.SmsLogMapper;
import com.futures.model.dto.SmsLogModifyDto;
import com.futures.model.dto.SmsLogSaveDto;
import com.futures.model.entity.SmsLog;
import com.futures.service.SmsLogService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsLogServiceImpl extends CrudServiceSupport<SmsLogMapper, SmsLog> implements SmsLogService {

    @Override
    public IPage<SmsLog> querySmsLog(IPage<SmsLog> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveSmsLog(SmsLogSaveDto smsLogSaveDto) {
        SmsLog smsLog = new SmsLog();
        BeanUtils.copyProperties(smsLogSaveDto, smsLog);
        smsLog.setAddTime(DateUtil.getNowDate());
        this.save(smsLog);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifySmsLog(SmsLogModifyDto smsLogModifyDto) {
        SmsLog smsLog = new SmsLog();
        BeanUtils.copyProperties(smsLogModifyDto, smsLog);
        this.updateById(smsLog);
    }
}