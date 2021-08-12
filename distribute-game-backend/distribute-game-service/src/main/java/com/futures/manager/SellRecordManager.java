package com.futures.manager;

import com.futures.common.enums.CommEnum;
import com.futures.mapper.SellRecordMapper;
import com.futures.model.entity.SellRecord;
import com.gongyu.snowcloud.framework.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 10:37
 */
@Service
public class SellRecordManager {

    @Autowired
    private SellRecordMapper recordMapper;

    public void convertAndInsert(Long userId, BigDecimal sellInom,Long pigLevel){
        SellRecord record = new SellRecord();
        record.setUserId(userId);
        record.setPigLevel(pigLevel);
        record.setSellIncom(sellInom);
        record.setSellDate(DateUtils.currentDate());
        record.setStatus(CommEnum.FALSE.getCode());
        recordMapper.insert(record);
    }


    public SellRecord getSellReco(Long id){
        return recordMapper.get(id);
    }

    /**
     * 查询出售推广收益记录
     * @param commEnum 0 未生成 1 已生成
     * @return
     */
    public List<SellRecord> findList(CommEnum commEnum){
        return recordMapper.findList(commEnum.getCode());
    }

    public List<SellRecord> findByUserAndNow(Long userId){
       return recordMapper.getByUserAndNow(userId);
    }

    public void updateIgnoreNull(SellRecord record){
        recordMapper.updateIgnoreNull(record);
    }
}
