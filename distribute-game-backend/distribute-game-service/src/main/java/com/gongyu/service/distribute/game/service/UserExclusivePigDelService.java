package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.PigDelPageReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDelDTO;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 21:05
 */
public interface UserExclusivePigDelService {

    IPage<UserExclusivePigDelDTO> findPage(IPage<UserExclusivePigDelDTO> page, PigDelPageReqDto param);
}
