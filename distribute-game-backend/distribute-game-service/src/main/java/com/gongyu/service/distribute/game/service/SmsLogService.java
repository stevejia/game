package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.SmsLogModifyDto;
import com.gongyu.service.distribute.game.model.dto.SmsLogSaveDto;
import com.gongyu.service.distribute.game.model.entity.SmsLog;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface SmsLogService extends CrudService<SmsLog>{

    IPage<SmsLog> querySmsLog(IPage<SmsLog> page);

    void saveSmsLog(SmsLogSaveDto smsLogSaveDto);

    void modifySmsLog(SmsLogModifyDto smsLogModifyDto);
}