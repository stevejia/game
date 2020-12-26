package com.gongyu.service.distribute.game.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.UserTeamLevelDto;
import com.gongyu.service.distribute.game.model.dto.UsersTreeResponseDto;
import com.gongyu.service.distribute.game.model.dto.newUsersDto;
import com.gongyu.service.distribute.game.model.entity.Users;

@Repository
public interface UsersMapper extends BaseMapper<Users> {

	List<UsersTreeResponseDto> queryTreeList(@Param("userId") Long userId, @Param("type") String type, @Param("todayZero")Long todayZero, @Param("threeDaysZero")Long threeDaysZero, @Param("sevenDaysZero")Long sevenDaysZero);

	List<newUsersDto> newUsers(@Param("startDate") String startDate, @Param("endDate") String endDate);

	List<UserTeamLevelDto> getTeamLevelNum(@Param("id") Long id);

	List<Users> queryUsers(IPage<Users> page, @Param("mobile") String mobile, @Param("id") Long id,
			@Param("regTimeStart") Long regTimeStart, @Param("regTimeEnd") Long regTimeEnd);
}