package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.SmsLogMapper;
import com.gongyu.service.distribute.game.model.dto.SmsLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.SmsLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.SmsLog;
import com.gongyu.service.distribute.game.service.SmsLogService;
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