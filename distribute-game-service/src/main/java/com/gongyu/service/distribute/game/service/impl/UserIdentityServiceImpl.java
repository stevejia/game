package com.gongyu.service.distribute.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gongyu.service.distribute.game.common.enums.AuthStatusEnum;
import com.gongyu.service.distribute.game.common.utils.DateUtil;
import com.gongyu.service.distribute.game.mapper.UserIdentityMapper;
import com.gongyu.service.distribute.game.model.dto.UserIdentityModifyDto;
import com.gongyu.service.distribute.game.model.dto.UserIdentitySaveDto;
import com.gongyu.service.distribute.game.model.entity.UserIdentity;
import com.gongyu.service.distribute.game.model.entity.Users;
import com.gongyu.service.distribute.game.service.UserIdentityService;
import com.gongyu.service.distribute.game.service.UsersService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserIdentityServiceImpl extends CrudServiceSupport<UserIdentityMapper, UserIdentity> implements UserIdentityService {

    @Autowired
    private UsersService usersService;

    @Override
    public IPage<UserIdentity> queryUserIdentity(IPage<UserIdentity> page, UserIdentityModifyDto userIdentityModifyDto) {
        LambdaQueryWrapper<UserIdentity> eq = Wrappers.<UserIdentity>lambdaQuery();
        if (userIdentityModifyDto.getUserId() != null) {
            eq.eq(UserIdentity::getUserId, userIdentityModifyDto.getUserId());
        }
        if (userIdentityModifyDto.getId() != null) {
            eq.eq(UserIdentity::getId, userIdentityModifyDto.getId());
        }
        if (userIdentityModifyDto.getStatus() != null) {
            eq.eq(UserIdentity::getStatus, userIdentityModifyDto.getStatus());
        }
        if (!StringUtils.isEmpty(userIdentityModifyDto.getMobile())) {
            List<Users> users = usersService.list(Wrappers.<Users>lambdaQuery().like(Users::getMobile, userIdentityModifyDto.getMobile()));
            if (CollectionUtils.isNotEmpty(users)) {
                users.stream().forEach(e -> {
                    eq.in(UserIdentity::getUserId, e.getId());
                });
            }
        }
        IPage<UserIdentity> page1 = this.page(page, eq);
        List<UserIdentity> records = page1.getRecords();
        List<Integer> collect = records.stream().map(UserIdentity::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return page1;
        }
        Collection<Users> users = usersService.listByIds(collect);
        if (CollectionUtils.isEmpty(users)) {
            return page1;
        }
        Map<Long, String> map = users.stream().collect(Collectors.toMap(Users::getId, Users::getMobile));
        records.stream().forEach(e -> {
            e.setMobile(map.get(Long.valueOf(e.getUserId())));
            Users u = usersService.getById(e.getUserId());
            e.setNickname(u.getNickname());
        });
        return page1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveUserIdentity(UserIdentitySaveDto userIdentitySaveDto) {
        UserIdentity userIdentity = new UserIdentity();
        BeanUtils.copyProperties(userIdentitySaveDto, userIdentity);
        userIdentity.setAddTime(DateUtil.getNowDate());
        userIdentity.setUpdateTime(DateUtil.getNowDate());
        this.save(userIdentity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyUserIdentity(UserIdentityModifyDto userIdentityModifyDto) {
        UserIdentity userIdentity = new UserIdentity();
        BeanUtils.copyProperties(userIdentityModifyDto, userIdentity);
        userIdentity.setUpdateTime(DateUtil.getNowDate());
        this.updateById(userIdentity);
    }

    @Override
    public UserIdentity convertUserIdentity(Long userId, String name, String idCard) {
        UserIdentity identity = new UserIdentity();
        identity.setUserId(Integer.valueOf(userId + ""));
        identity.setRealName(name);
        identity.setIdentity(idCard);
        identity.setStatus(AuthStatusEnum.AUTH_SUCCESS.getCode());
        identity.setAddTime(DateUtil.getNowDate());
        identity.setUpdateTime(DateUtil.getNowDate());
        return identity;
    }
}