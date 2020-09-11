package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyu.service.distribute.game.model.dto.UserTeamLevelDto;
import com.gongyu.service.distribute.game.model.dto.UsersTreeResponseDto;
import com.gongyu.service.distribute.game.model.dto.newUsersDto;
import com.gongyu.service.distribute.game.model.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersMapper extends BaseMapper<Users> {

    List<UsersTreeResponseDto> queryTreeList(@Param("userId") Long userId, @Param("type") String type);

    List<newUsersDto> newUsers(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<UserTeamLevelDto> getTeamLevelNum(@Param("id") Long id);
}