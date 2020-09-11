package com.gongyu.service.distribute.game.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.UserExclusivePigDeleteMapper;
import com.gongyu.service.distribute.game.model.dto.PigDeletePageDTO;
import com.gongyu.service.distribute.game.model.dto.PigDeletePageReqDto;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePigDelete;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 14:52
 */
@Service
public class UserExclusvePigDeleteManager {

    @Autowired
    private UserExclusivePigDeleteMapper deleteMapper;

    public UserExclusivePigDelete convert(UserExclusivePig exclusivePig,String remark){
        UserExclusivePigDelete bean = new UserExclusivePigDelete();
        bean.setOrderId(exclusivePig.getOrderId());
        bean.setPigId(exclusivePig.getPigId());
        bean.setIsAbleSale(exclusivePig.getIsAbleSale());
        bean.setPrice(exclusivePig.getPrice());
        bean.setFromUserId(exclusivePig.getFromUserId());
        bean.setAppointUserId(exclusivePig.getAppointUserId());
        bean.setBuyTime(exclusivePig.getBuyTime());
        bean.setEndTime(exclusivePig.getEndTime());
        bean.setDelId(exclusivePig.getId());
        bean.setDeltime(DateUtil.getNowDate());
        bean.setUserId(exclusivePig.getUserId());
        bean.setRemark(StringUtils.isBlank(remark) ? StringUtils.EMPTY : remark);
        return bean;
    }

    public Long insert(UserExclusivePigDelete bean){
        deleteMapper.insert(bean);
        return bean.getId();
    }

    public List<PigDeletePageDTO> findPage(IPage<PigDeletePageDTO> page, PigDeletePageReqDto param){
        return deleteMapper.findPage(page,param);
    }
}
