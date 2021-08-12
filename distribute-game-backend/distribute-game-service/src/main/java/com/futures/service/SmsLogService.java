package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.SmsLogModifyDto;
import com.futures.model.dto.SmsLogSaveDto;
import com.futures.model.entity.SmsLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SmsLogService extends CrudService<SmsLog>{

    IPage<SmsLog> querySmsLog(IPage<SmsLog> page);

    void saveSmsLog(SmsLogSaveDto smsLogSaveDto);

    void modifySmsLog(SmsLogModifyDto smsLogModifyDto);
}