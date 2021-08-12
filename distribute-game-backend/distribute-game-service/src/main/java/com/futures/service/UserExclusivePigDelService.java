package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.PigDelPageReqDto;
import com.futures.model.dto.UserExclusivePigDelDTO;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 21:05
 */
public interface UserExclusivePigDelService {

    IPage<UserExclusivePigDelDTO> findPage(IPage<UserExclusivePigDelDTO> page, PigDelPageReqDto param);
}
