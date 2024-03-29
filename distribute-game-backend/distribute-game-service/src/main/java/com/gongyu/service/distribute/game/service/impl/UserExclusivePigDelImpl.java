package com.gongyu.service.distribute.game.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.enums.SaleStatusEnum;
import com.gongyu.service.distribute.game.manager.UserExclusivePigDelManager;
import com.gongyu.service.distribute.game.model.dto.PigDelPageReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDelDTO;
import com.gongyu.service.distribute.game.service.UserExclusivePigDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 21:07
 */
@Service
public class UserExclusivePigDelImpl implements UserExclusivePigDelService {

    @Autowired
    private UserExclusivePigDelManager delManager;

    @Override
    public IPage<UserExclusivePigDelDTO> findPage(IPage<UserExclusivePigDelDTO> page, PigDelPageReqDto param) {
        List<UserExclusivePigDelDTO> list = delManager.findPage(page, param);
        list.forEach(item ->{
            item.setSplitTime(Long.valueOf(item.getDeltime()));
            item.setSaleStatus(SaleStatusEnum.parse(item.getIsAbleSale()).getStatus());
        });
        page.setRecords(list);
        return page;
    }
}
