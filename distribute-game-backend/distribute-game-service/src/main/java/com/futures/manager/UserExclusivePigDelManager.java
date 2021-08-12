package com.futures.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.UserExclusivePigDelMapper;
import com.futures.model.dto.PigDelPageReqDto;
import com.futures.model.dto.UserExclusivePigDelDTO;
import com.futures.model.entity.UserExclusivePig;
import com.futures.model.entity.UserExclusivePigDel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 19:58
 */
@Service
public class UserExclusivePigDelManager {

    @Autowired
    private UserExclusivePigDelMapper delMapper;


    public List<UserExclusivePigDelDTO> findPage(IPage<UserExclusivePigDelDTO> page,PigDelPageReqDto param){
        return delMapper.findPage(page,param);
    }

    public List<UserExclusivePigDel> findPageByUser(IPage page, Long userId){
        return delMapper.findPageByUser(page,userId);
    }

    public Long insert(UserExclusivePigDel pigDel){
        delMapper.add(pigDel);
        return pigDel.getDelid();
    }

    public UserExclusivePigDel convert(UserExclusivePig pig){
        UserExclusivePigDel pigDel = new UserExclusivePigDel();
        pigDel.setUserId(pig.getUserId());
        pigDel.setOrderId(pig.getOrderId());
        pigDel.setPigId(pig.getPigId());
        pigDel.setIsAbleSale(pig.getIsAbleSale());
        pigDel.setPrice(pig.getPrice());
        pigDel.setFromUserId(pig.getFromUserId());
        pigDel.setAppointUserId(pig.getAppointUserId());
        pigDel.setBuyTime(pig.getBuyTime());
        pigDel.setEndTime(pig.getEndTime());
        pigDel.setType("del");
        pigDel.setDeltime(String.valueOf(DateUtil.getNowDate()));
        return pigDel;
    }
}
