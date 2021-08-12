package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futures.model.entity.SysUserLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserLogMapper extends BaseMapper<SysUserLog> {

    void saveSysUserLog(@Param("sysUserLog") SysUserLog sysUserLog);

    List<String> queryUserLogModes();

}
