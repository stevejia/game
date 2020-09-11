package com.gongyu.service.distribute.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.RechargeDto;
import com.gongyu.service.distribute.game.model.entity.Recharge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RechargeMapper extends BaseMapper<Recharge> {

    List<RechargeDto> findPage(IPage<RechargeDto> page, @Param("param") RechargeDto dto);

    @Select("select sum(account) from zp_recharge where user_id = #{userId} and pay_status = 1")
    BigDecimal countAccount(@Param("userId")Long userId);
}