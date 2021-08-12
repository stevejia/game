package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.enums.BuyTypeEnum;
import com.futures.common.enums.LockStatusEnum;
import com.futures.common.enums.SaleStatusEnum;
import com.futures.manager.UserExclusivePigManager;
import com.futures.mapper.UserExclusivePigMapper;
import com.futures.model.dto.*;
import com.futures.model.entity.UserExclusivePig;
import com.futures.service.UserExclusivePigService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserExclusivePigServiceImpl extends CrudServiceSupport<UserExclusivePigMapper, UserExclusivePig> implements UserExclusivePigService  {

    @Autowired
    private UserExclusivePigManager pigManager;

    @Override
    public IPage<UserExclusivePigDTO> queryUserExclusivePig(IPage<UserExclusivePigDTO> page, PigPageReqDto param) {
        List<UserExclusivePigDTO> list = pigManager.findPage(page, param);
        list.forEach(item ->{
            item.setSaleStatus(SaleStatusEnum.parse(item.getIsAbleSale()).getGoodsStatus());
            item.setIsPigLockStatus(LockStatusEnum.parse(item.getIsPigLock()).getDesc());
            item.setGoodsType(BuyTypeEnum.parse(item.getBuyType()).getDesc());
        });
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<UserExclusivePigDTO> prizeToday(IPage<UserExclusivePigDTO> page, PrizeTodayReqDto param) {
        List<UserExclusivePigDTO> list = pigManager.prizeToday(page,param);
        list.forEach(item ->{
            item.setIsPigLockStatus(LockStatusEnum.parse(item.getIsPigLock()).getDesc());
            item.setSaleStatus(SaleStatusEnum.parse(item.getIsAbleSale()).getDesc());
            item.setGoodsType(BuyTypeEnum.parse(item.getBuyType()).getDesc());
        });
        for(UserExclusivePigDTO dto : list) {

            if(dto.getAppointUserId()!= null && dto.getAppointUserId().longValue() == 0) {

                dto.setAppointUserId(null);
            }
        }
        page.setRecords(list);
        return page;
    }
    @Override
    public List<UserExclusivePigDTO> prizeAllToday( PrizeTodayReqDto param) {
        List<UserExclusivePigDTO> list = pigManager.prizeAllToday(param);
        list.forEach(item ->{
            item.setIsPigLockStatus(LockStatusEnum.parse(item.getIsPigLock()).getDesc());
            item.setSaleStatus(SaleStatusEnum.parse(item.getIsAbleSale()).getDesc());
            item.setGoodsType(BuyTypeEnum.parse(item.getBuyType()).getDesc());
        });
        for(UserExclusivePigDTO dto : list) {

            if(dto.getAppointUserId()!= null && dto.getAppointUserId().longValue() == 0) {

                dto.setAppointUserId(null);
            }
        }
        return list;
    }
    

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserExclusivePig(UserExclusivePigSaveDto userExclusivePigSaveDto) {
        UserExclusivePig userExclusivePig = new UserExclusivePig();
        BeanUtils.copyProperties(userExclusivePigSaveDto, userExclusivePig);
        this.save(userExclusivePig);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserExclusivePig(UserExclusivePigModifyDto userExclusivePigModifyDto) {
        UserExclusivePig userExclusivePig = new UserExclusivePig();
        BeanUtils.copyProperties(userExclusivePigModifyDto, userExclusivePig);
        this.updateById(userExclusivePig);
    }

    @Override
    public void updatePrice(PigUpdatePriceReqDto param) {
        UserExclusivePig pig = this.getById(param.getId());
        Assert.notNull(pig,"没有找到对应实例");
        pig.setPrice(param.getPrice());
        this.updateById(pig);
    }

    @Override
    public void updateAppointUserId(PigUpdateUserIdReqDto param) {
        UserExclusivePig pig = this.getById(param.getId());
        Assert.notNull(pig,"没有找到对应实例");
        pig.setAppointUserId(param.getAppointUserId());
        this.updateById(pig);
    }
}