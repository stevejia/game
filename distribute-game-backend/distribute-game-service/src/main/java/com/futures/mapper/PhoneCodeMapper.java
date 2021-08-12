package com.futures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futures.model.entity.PhoneCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneCodeMapper extends BaseMapper<PhoneCode> {
    @Select({"select * from phone_code where phone=#{1} and code_type=#{2} and be_used = 'N' and expires_date > now() and deleted = 0 ORDER BY createDate DESC limit 1"})
    PhoneCode queryByPhoneType(@Param("1") String phone, @Param("2") String codeType);
}
