package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.UserPaymentMapper;
import com.gongyu.service.distribute.game.model.dto.UserPaymentModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserPaymentSaveDto;
import com.gongyu.service.distribute.game.model.entity.UserPayment;
import com.gongyu.service.distribute.game.service.UserPaymentService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import com.gongyu.snowcloud.framework.web.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPaymentServiceImpl extends CrudServiceSupport<UserPaymentMapper, UserPayment> implements UserPaymentService {

    @Override
    public IPage<UserPayment> queryUserPayment(IPage<UserPayment> page) {
        return this.page(page, new QueryWrapper<UserPayment>().orderByDesc("id"));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserPayment(UserPaymentSaveDto userPaymentSaveDto) {
        UserPayment userPayment = new UserPayment();
        BeanUtils.copyProperties(userPaymentSaveDto, userPayment);
        userPayment.setCreateTime(DateUtil.getNowDate());
        this.save(userPayment);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserPayment(UserPaymentModifyDto userPaymentModifyDto) {
        UserPayment userPayment = new UserPayment();
        BeanUtils.copyProperties(userPaymentModifyDto, userPayment);
        this.updateById(userPayment);
    }

    @Override
    public boolean checkProperPayment() {
        List<UserPayment> list = this.list(new QueryWrapper<UserPayment>().eq("user_id", WebUtils.getCurrentUserId()).eq("deleted", 0));
        list = list.stream()
                .filter(item -> item.getStatus().intValue() == 1)
                .collect(Collectors.toList());

        return !CollectionUtils.isEmpty(list);
    }


}