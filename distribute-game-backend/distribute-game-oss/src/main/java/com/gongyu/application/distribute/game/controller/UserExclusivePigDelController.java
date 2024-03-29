package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigDelPageReqDto;
import com.gongyu.service.distribute.game.model.dto.UserExclusivePigDelDTO;
import com.gongyu.service.distribute.game.service.UserExclusivePigDelService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 10:46
 */
@RestController
@RequestMapping("userExclusivePigDel")
@Api(tags = "裂变精灵管理")
public class UserExclusivePigDelController {

    @Autowired
    private UserExclusivePigDelService delService;

    @ApiOperation(value = "裂变精灵 - 列表",notes = "裂变精灵 - 列表",response = UserExclusivePigDelDTO.class)
    @PostMapping("findPage")
    public BaseResponse findPage(Page page, PigDelPageReqDto param){
        return BaseResponse.success(delService.findPage(page,param));
    }


}
