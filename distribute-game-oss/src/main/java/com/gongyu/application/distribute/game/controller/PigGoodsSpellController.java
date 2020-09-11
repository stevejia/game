package com.gongyu.application.distribute.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellModifyDto;
import com.gongyu.service.distribute.game.model.dto.PigGoodsSpellSaveDto;
import com.gongyu.service.distribute.game.model.entity.PigGoodsSpell;
import com.gongyu.service.distribute.game.service.PigGoodsSpellService;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pigGoodsSpell")
@Api(tags = "商品合成管理")
public class PigGoodsSpellController {

    @Autowired
    private PigGoodsSpellService pigGoodsSpellService;

    @ApiOperation(value = "【商品合成管理】列表", notes = "【商品合成管理】列表", response = PigGoodsSpell.class)
    @PostMapping("queryPigGoodsSpell")
    public BaseResponse queryPigGoodsSpell(Page page) {
        return BaseResponse.success(pigGoodsSpellService.queryPigGoodsSpell(page));
    }

    @ApiOperation(value = "【商品合成管理】详情", notes = "【商品合成管理】详情", response = PigGoodsSpell.class)
    @PostMapping("getPigGoodsSpell")
    public BaseResponse getPigGoodsSpell(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        return BaseResponse.success(pigGoodsSpellService.getById(id));
    }

    @ApiOperation(value = "【商品合成管理】添加", notes = "【商品合成管理】添加")
    @PostMapping("savePigGoodsSpell")
    @SysUserLog(module = "商品合成管理", action = "添加")
    public BaseResponse savePigGoodsSpell(@Valid @ModelAttribute PigGoodsSpellSaveDto pigGoodsSpellSaveDto) {
        pigGoodsSpellService.savePigGoodsSpell(pigGoodsSpellSaveDto);
        return BaseResponse.success("添加成功");
    }

    @ApiOperation(value = "【商品合成管理】修改", notes = "【商品合成管理】修改")
    @PostMapping("modifyPigGoodsSpell")
    @SysUserLog(module = "商品合成管理", action = "修改")
    public BaseResponse modifyPigGoodsSpell(@Valid @ModelAttribute PigGoodsSpellModifyDto pigGoodsSpellModifyDto) {
        pigGoodsSpellService.modifyPigGoodsSpell(pigGoodsSpellModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【商品合成管理】删除", notes = "【商品合成管理】删除")
    @PostMapping("deletePigGoodsSpell")
    @SysUserLog(module = "商品合成管理", action = "删除")
    public BaseResponse deletePigGoodsSpell(@ApiParam(value = "id", required = true) @RequestParam()Long id) {
        pigGoodsSpellService.removeById(id);
        return BaseResponse.success("删除成功");
    }
}