package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futures.mapper.DistributLevelMapper;
import com.futures.model.dto.DistributLevelModifyDto;
import com.futures.model.dto.DistributLevelSaveDto;
import com.futures.model.dto.UsersTreeResponseDto;
import com.futures.model.entity.DistributLevel;
import com.futures.model.entity.Users;
import com.futures.service.DistributLevelService;
import com.futures.service.UsersService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributLevelServiceImpl extends CrudServiceSupport<DistributLevelMapper, DistributLevel> implements DistributLevelService {

    @Autowired
    private UsersService usersService;

    @Override
    public IPage<DistributLevel> queryDistributLevel(IPage<DistributLevel> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveDistributLevel(DistributLevelSaveDto distributLevelSaveDto) {
        DistributLevel distributLevel = new DistributLevel();
        BeanUtils.copyProperties(distributLevelSaveDto, distributLevel);
        this.save(distributLevel);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyDistributLevel(DistributLevelModifyDto distributLevelModifyDto) {
        DistributLevel distributLevel = new DistributLevel();
        BeanUtils.copyProperties(distributLevelModifyDto, distributLevel);
        this.updateById(distributLevel);
    }

    @Override
    public List<UsersTreeResponseDto> treeList(Long userId, String mobile, String type) {
        if (!StringUtils.isEmpty(mobile)) {
            Users one = usersService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getMobile, mobile));
            if (one != null) {
                userId = one.getId();
            }
        }
        List<UsersTreeResponseDto> list = usersService.queryTreeList(userId, type);
        return list;
    }
}