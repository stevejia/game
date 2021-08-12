package com.futures.controller.admin;

import com.futures.model.dto.SysDictItemModifyDto;
import com.futures.model.dto.SysDictItemSaveDto;
import com.futures.model.dto.SysDictModifyDto;
import com.futures.model.dto.SysDictSaveDto;
import com.futures.model.entity.SysDict;
import com.futures.model.entity.SysDictItem;
import com.futures.service.SysDictFpi;
import com.gongyu.snowcloud.framework.base.response.BaseResponse;
import com.gongyu.snowcloud.framework.data.mybatis.Pageable;
import com.gongyu.snowcloud.framework.log.SysUserLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sys/dict")
@Api(tags = "系统管理")
public class SysDictController {
    @Autowired
    private SysDictFpi sysDictFpi;

    @ApiOperation(value = "【字典管理】-字典列表", notes = "字典管理-字典列表", response = SysDict.class)
    @PostMapping("querySysDict")
    public BaseResponse querySysDict(Pageable page, @ApiParam(value = "字典码") String dictCode) {
        return BaseResponse.success(sysDictFpi.querySysDict(page.getPage(), dictCode));
    }

    @ApiOperation(value = "【字典管理】-新增字典", notes = "字典管理-新增字典")
    @PostMapping("saveSysDict")
    @SysUserLog(module = "字典管理", action = "新增字典")
    public BaseResponse saveSysDict(@Valid @ModelAttribute SysDictSaveDto sysDictSaveDto) {
        return BaseResponse.success(sysDictFpi.saveSysDict(sysDictSaveDto));
    }

    @ApiOperation(value = "【字典管理】-修改字典", notes = "字典管理-修改字典")
    @PostMapping("modifySysDict")
    @SysUserLog(module = "字典管理", action = "修改字典")
    public BaseResponse modifySysDict(@Valid @ModelAttribute SysDictModifyDto sysDictModifyDto) {
        return BaseResponse.success(sysDictFpi.modifySysDict(sysDictModifyDto));
    }

    @ApiOperation(value = "【字典管理】-删除字典", notes = "字典管理-删除字典")
    @PostMapping("deleteSysDict")
    @SysUserLog(module = "字典管理", action = "删除字典")
    public BaseResponse deleteSysDict(@ApiParam(value = "字典ID", required = true) @RequestParam() Long id) {
        return BaseResponse.success(sysDictFpi.deleteSysDict(id));
    }

    @ApiOperation(value = "【字典管理】-字典明细列表", notes = "字典管理-字典明细列表", response = SysDictItem.class)
    @PostMapping("querySysDictItem")
    public BaseResponse querySysDictItem(Pageable page, @ApiParam(value = "字典编码", required = true) @RequestParam() String dictCode) {
        return BaseResponse.success(sysDictFpi.querySysDictItem(page.getPage(), dictCode));
    }

    @ApiOperation(value = "【字典管理】-新增字典明细", notes = "字典管理-新增字典明细")
    @PostMapping("saveSysDictItem")
    @SysUserLog(module = "字典管理", action = "新增字典明细")
    public BaseResponse saveSysDictItem(@Valid @ModelAttribute SysDictItemSaveDto sysDictItemSaveDto) {
        sysDictFpi.saveSysDictItem(sysDictItemSaveDto);
        return BaseResponse.success("保存成功");
    }

    @ApiOperation(value = "【字典管理】-修改字典明细", notes = "字典管理-修改字典明细")
    @PostMapping("modifySysDictItem")
    @SysUserLog(module = "字典管理", action = "修改字典明细")
    public BaseResponse modifySysDictItem(@Valid @ModelAttribute SysDictItemModifyDto sysDictItemModifyDto) {
        sysDictFpi.modifySysDictItem(sysDictItemModifyDto);
        return BaseResponse.success("修改成功");
    }

    @ApiOperation(value = "【字典管理】-删除字典明细", notes = "字典管理-删除字典明细")
    @PostMapping("deleteSysDictItem")
    @SysUserLog(module = "字典管理", action = "删除字典明细")
    public BaseResponse deleteSysDictItem(@ApiParam(value = "字典明细ID", required = true) @RequestParam() Long id) {
        return BaseResponse.success(sysDictFpi.deleteSysDictItem(id));
    }

    @ApiOperation(value = "【字典管理】-字典码查询所有字典项", notes = "字典管理-字典码查询所有字典项", response = SysDictItem.class)
    @RequestMapping(value = "getSysDictItemList", method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse getSysDictItemList(@ApiParam(value = "字典编码", required = true) @RequestParam() String dictCode) {
        return BaseResponse.success(sysDictFpi.getSysDictItemList(dictCode));
    }
}
