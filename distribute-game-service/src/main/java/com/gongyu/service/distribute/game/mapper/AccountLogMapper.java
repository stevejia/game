package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.entity.AccountLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountLogMapper extends BaseMapper<AccountLog> {

    List<AccountLog> findPage(IPage<AccountLog> page, @Param("userId") Long userId,@Param("type") String type);
}