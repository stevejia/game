package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.common.enums.SaleStatusEnum;
import com.futures.manager.UserExclusvePigDeleteManager;
import com.futures.mapper.UserExclusivePigDeleteMapper;
import com.futures.model.dto.*;
import com.futures.model.entity.UserExclusivePig;
import com.futures.model.entity.UserExclusivePigDelete;
import com.futures.service.UserExclusivePigDeleteService;
import com.futures.service.UserExclusivePigService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserExclusivePigDeleteServiceImpl extends CrudServiceSupport<UserExclusivePigDeleteMapper, UserExclusivePigDelete> implements UserExclusivePigDeleteService  {

    @Autowired
    private UserExclusivePigService userExclusivePigService;
    @Autowired
    private UserExclusvePigDeleteManager pigDeleteManager;
    @Override
    public IPage<PigDeletePageDTO> queryUserExclusivePigDelete(IPage<PigDeletePageDTO> page, PigDeletePageReqDto param) {
        List<PigDeletePageDTO> list = pigDeleteManager.findPage(page, param);
        list.forEach(item ->{
            item.setSaleStatus(SaleStatusEnum.parse(item.getIsAbleSale()).getStatus());
        });
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserExclusivePigDelete(UserExclusivePigDeleteSaveDto userExclusivePigDeleteSaveDto) {
        UserExclusivePigDelete userExclusivePigDelete = new UserExclusivePigDelete();
        BeanUtils.copyProperties(userExclusivePigDeleteSaveDto, userExclusivePigDelete);
        this.save(userExclusivePigDelete);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserExclusivePigDelete(UserExclusivePigDeleteModifyDto userExclusivePigDeleteModifyDto) {
        UserExclusivePigDelete userExclusivePigDelete = new UserExclusivePigDelete();
        BeanUtils.copyProperties(userExclusivePigDeleteModifyDto, userExclusivePigDelete);
        this.updateById(userExclusivePigDelete);
    }

    @Transactional
    @Override
    public BaseResponse destoryPig(DestoryPigReqDto param) {
        UserExclusivePig exclusivePig = userExclusivePigService.getById(param.getId());
        if(null == exclusivePig){
            log.warn("销毁木材警告 destoryPig exclusivePig is null pigId:{}",param.getId());
            throw new BizException("没有对应木材实例");
        }
        UserExclusivePigDelete pigDeleteDB = pigDeleteManager.convert(exclusivePig,param.getRemark());
        Long insertId = pigDeleteManager.insert(pigDeleteDB);
        log.info("销毁木材 添加销毁木材实例成功 pigId:{},insertId:{}",param.getId(),insertId);
        userExclusivePigService.removeById(param.getId());
        return BaseResponse.success("销毁成功");
    }
}