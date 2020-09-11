package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.DistributLevelModifyDto;
import com.gongyu.service.distribute.game.model.dto.DistributLevelSaveDto;
import com.gongyu.service.distribute.game.model.dto.UsersTreeResponseDto;
import com.gongyu.service.distribute.game.model.entity.DistributLevel;
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