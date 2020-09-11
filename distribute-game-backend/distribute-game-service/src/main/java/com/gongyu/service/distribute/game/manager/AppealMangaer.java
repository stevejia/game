package com.gongyu.service.distribute.game.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.common.enums.AppealStatusEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.PigAppealMapper;
import com.gongyu.service.distribute.game.model.dto.AppealReqDto;
import com.gongyu.service.distribute.game.model.entity.PigAppeal;
import com.gongyu.service.distribute.game.model.entity.PigOrder;
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
