package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futures.common.utils.DateUtil;
import com.futures.mapper.UserPaymentMapper;
import com.futures.model.dto.UserPaymentModifyDto;
import com.futures.model.dto.UserPaymentSaveDto;
import com.futures.model.entity.UserPayment;
import com.futures.model.entity.UserPaymentLog;
import com.futures.service.UserPaymentService;
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

    @SuppressWarnings("unchecked")
	@Override
    public IPage<UserPayment> queryUserPayment(Integer userId, Integer status, IPage<UserPayment> page) {
    	 LambdaQueryWrapper<UserPayment> qw = Wrappers.<UserPayment>lambdaQuery();
    	if(null != userId) {
    		qw.eq(UserPayment::getUserId, userId);
    	}
    	
    	if(null != status) {
    		qw.eq(UserPayment::getStatus, status);
    	}
    	
    	qw.orderByDesc(UserPayment::getId);
        return this.page(page, qw);
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