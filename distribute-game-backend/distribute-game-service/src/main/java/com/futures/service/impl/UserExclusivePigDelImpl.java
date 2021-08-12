package com.futures.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.enums.SaleStatusEnum;
import com.futures.manager.UserExclusivePigDelManager;
import com.futures.model.dto.PigDelPageReqDto;
import com.futures.model.dto.UserExclusivePigDelDTO;
import com.futures.service.UserExclusivePigDelService;
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
