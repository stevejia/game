package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigPageReqDto;
import com.gongyu.service.distribute.game.model.dto.PrizeTodayReqDto;
import com.gongyu.service.distribute.game.model.dto.TransfReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDTO;
import com.gongyu.service.distribute.game.model.entity.UserExclusivePig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExclusivePigMapper extends BaseMapper<UserExclusivePig> {

    List<UserExclusivePigDTO> findPage(IPage<UserExclusivePigDTO> page, @Param("param") PigPageReqDto pageParam);

    @Select("select * from zp_user_exclusive_pig where user_id = #{userId} and is_able_sale = #{ableSale} and is_pig_lock = 0")
    List<UserExclusivePig> findPageByUser(IPage page, @Param("userId") Long userId,@Param("ableSale") Integer ableSale, @Param("param") TransfReqDto param);

    List<UserExclusivePigDTO> prizeToday(IPage<UserExclusivePigDTO> page, @Param("param") PrizeTodayReqDto param);
    List<UserExclusivePigDTO> prizeAllToday(@Param("param") PrizeTodayReqDto param);

    int insertForeach(List<UserExclusivePig> list);

    int updateForeach(List<UserExclusivePig> list);

    @Select("select * from zp_user_exclusive_pig where is_pig_lock = 0 and is_able_sale = 0")
    List<UserExclusivePig> findList();

    @Select("select * from zp_user_exclusive_pig where order_id = #{orderId}")
    UserExclusivePig findByOrderId(@Param("orderId") Long orderId);
}