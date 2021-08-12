package com.futures.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futures.common.enums.AppealStatusEnum;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.PigAppealMapper;
import com.futures.model.dto.AppealReqDto;
import com.futures.model.entity.PigAppeal;
import com.futures.model.entity.PigOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/22 20:12
 */
@Slf4j
@Service
public class AppealMangaer {

    @Autowired
    private PigAppealMapper appealMapper;

    public List<PigAppeal> findPage(IPage page, Long userId){
        return appealMapper.findPage(page,userId);
    }

    public void convertAndInsert(PigOrder order, AppealStatusEnum appealStatusEnum, AppealReqDto param){
        PigAppeal appeal = new PigAppeal();
        if(1 == param.getComplainantType().intValue()){
            appeal.setUserId(order.getPurchaseUserId());
        }else{
            appeal.setUserId(order.getSellUserId());
        }
        appeal.setOrderId(order.getOrderId());
        appeal.setRemark(param.getAppealDesc());
        appeal.setAddTime(order.getEstablishTime());
        appeal.setImgUrl(param.getAppealImg());
        appeal.setStatus(appealStatusEnum.getCode());
        appeal.setUpdateTime(DateUtil.getNowDate());
        appeal.setComplainant(param.getComplainantType());
        appealMapper.insert(appeal);
    }
}
