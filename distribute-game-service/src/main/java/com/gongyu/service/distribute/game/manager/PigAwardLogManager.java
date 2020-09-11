package com.gongyu.service.distribute.game.manager;

import com.gongyu.service.distribute.game.common.enums.OpenResultEnum;
import com.gongyu.service.distribute.game.mapper.PigAwardLogMapper;
import com.gongyu.service.distribute.game.model.entity.PigAwardLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/8 17:27
 */
@Service
public class PigAwardLogManager {

    @Autowired
    private PigAwardLogMapper awardLogMapper;

    public PigAwardLog convert(Long pigId,OpenResultEnum openResultEnum){
        PigAwardLog bean = new PigAwardLog();
        bean.setPigId(pigId);
        bean.setJoinUserList(StringUtils.EMPTY);
        bean.setAwardUserList(StringUtils.EMPTY);
        bean.setChangeTime(0L);
        bean.setPigList(StringUtils.EMPTY);
        bean.setOpenResult(openResultEnum.getCode());
        return bean;

    }

    @Transactional
    public PigAwardLog insert(PigAwardLog bean){
        awardLogMapper.insert(bean);
        return bean;
    }
}
