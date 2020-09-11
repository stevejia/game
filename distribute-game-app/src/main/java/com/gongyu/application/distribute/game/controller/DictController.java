package com.gongyu.application.distribute.game.controller;

import com.gongyu.service.distribute.game.model.entity.SysDictItem;
import com.gongyu.service.distribute.game.service.SysDictFpi;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"dict"})
@Api(
        tags = {"字典查询"}
)
public class DictController {


    @Autowired
    private SysDictFpi sysDictFpi;

    @ApiOperation(
            value = "根据字典码查询字典项",
            notes = "根据字典码查询字典项",
            response = SysDictItem.class
    )
    @RequestMapping(
            value = {"getSysDictItemList"},
            method = {RequestMethod.POST}
    )
    public BaseResponse getSysDictItemList(@ApiParam(value = "字典码", required = true) @RequestParam String dictCode) {
        return BaseResponse.success(this.sysDictFpi.getSysDictItemList(dictCode));
    }
}
