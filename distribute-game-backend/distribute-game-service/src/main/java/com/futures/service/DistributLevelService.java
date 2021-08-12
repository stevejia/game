package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.DistributLevelModifyDto;
import com.futures.model.dto.DistributLevelSaveDto;
import com.futures.model.dto.UsersTreeResponseDto;
import com.futures.model.entity.DistributLevel;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

import java.util.List;

public interface DistributLevelService extends CrudService<DistributLevel> {

    IPage<DistributLevel> queryDistributLevel(IPage<DistributLevel> page);

    void saveDistributLevel(DistributLevelSaveDto distributLevelSaveDto);

    void modifyDistributLevel(DistributLevelModifyDto distributLevelModifyDto);

    /**
     * 查询分销等级树
     *
     * @param userId 用户ID
     * @param mobile 用户手机号
     * @return
     */
    List<UsersTreeResponseDto> treeList(Long userId, String mobile, String type);
}