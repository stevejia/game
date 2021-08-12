package com.futures.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.*;
import com.futures.model.entity.SysDict;
import com.futures.model.entity.SysDictItem;
import com.futures.service.SysDictFpi;
import com.futures.service.SysDictItemService;
import com.futures.service.SysDictService;
import com.gongyu.snowcloud.framework.base.exception.BizException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SysDictFpiImpl implements SysDictFpi {
    public static final Pattern pattern = Pattern.compile("(\\{[^}]*})");

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    public IPage<SysDict> querySysDict(IPage<SysDict> page, String dictCode) {
        QueryWrapper<SysDict> sysDictWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(dictCode)){
            sysDictWrapper = sysDictWrapper.eq("dict_code",dictCode);
        }
        sysDictWrapper = sysDictWrapper.orderByDesc("id");
        return sysDictService.page(page, sysDictWrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public SysDict saveSysDict(SysDictSaveDto sysDictSaveDto) {
        if(sysDictService.exists("dict_code", sysDictSaveDto.getDictCode())){
            throw new BizException("字典【" + sysDictSaveDto.getDictCode() + "】已存在");
        }
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictSaveDto, sysDict);
        sysDictService.save(sysDict);
        return sysDict;
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public SysDict modifySysDict(SysDictModifyDto sysDictModifyDto) {
        SysDict sysDict = sysDictService.getById(sysDictModifyDto.getId());
        if (sysDict == null) {
            throw new BizException("没有该字典,或已被删除");
        }
        if(sysDictService.exists(new QueryWrapper<SysDict>().eq("deleted",0).eq("dict_code", sysDictModifyDto.getDictCode()).ne("id",sysDictModifyDto.getId()))) {
            throw new BizException("字典【" + sysDictModifyDto.getDictCode() + "】已存在");
        }
        SysDict dict = new SysDict();
        BeanUtils.copyProperties(sysDictModifyDto, dict);
        sysDictService.updateById(dict);
        return dict;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public boolean deleteSysDict(Long id) {
        return sysDictService.removeById(id);
    }

    @Override
    public IPage<SysDictItem> querySysDictItem(IPage<SysDictItem> page, String dictCode) {
        QueryWrapper<SysDictItem> sysDictItemWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(dictCode)){
            sysDictItemWrapper = sysDictItemWrapper.eq("dict_code",dictCode);
        }
        sysDictItemWrapper = sysDictItemWrapper.orderByDesc("createDate");
        return sysDictItemService.page(page, sysDictItemWrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveSysDictItem(SysDictItemSaveDto sysDictItemSaveDto) {
        if(sysDictItemService.exists(new QueryWrapper<SysDictItem>().eq("deleted",0).eq("dict_code", sysDictItemSaveDto.getDictCode()).eq("item_code", sysDictItemSaveDto.getItemCode()))){
            throw new BizException("字典明细【" + sysDictItemSaveDto.getItemCode() + "】已存在");
        }
        SysDictItem baseDictItem = new SysDictItem();
        BeanUtils.copyProperties(sysDictItemSaveDto, baseDictItem);
        sysDictItemService.save(baseDictItem);
    }

    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class, RuntimeException.class})
    public void modifySysDictItem(SysDictItemModifyDto sysDictItemModifyDto) {
        SysDictItem sysDictItem = sysDictItemService.getById(sysDictItemModifyDto.getId());
        if(null==sysDictItem){
            throw new BizException("该字典明细不存在,或已被删除");
        }
        if(sysDictItemService.exists(new QueryWrapper<SysDictItem>().eq("deleted",0).eq("dict_code", sysDictItem.getDictCode()).eq("item_code", sysDictItemModifyDto.getItemCode()).ne("id",sysDictItemModifyDto.getId()))) {
            throw new BizException("字典明细【" + sysDictItemModifyDto.getItemCode() + "】已存在");
        }
        SysDictItem dictItem = new SysDictItem();
        BeanUtils.copyProperties(sysDictItemModifyDto, dictItem);
        sysDictItemService.updateById(dictItem);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public boolean deleteSysDictItem(Long id) {
        return sysDictItemService.removeById(id);
    }

    @Override
    public List<SysDictItem> getSysDictItemList(String dictCode) {
        List<SysDictItem> sysDictItemList = sysDictItemService.list(new QueryWrapper<SysDictItem>().eq("dict_code", dictCode));
        for(SysDictItem sysDictItem:sysDictItemList){
            if(!org.springframework.util.StringUtils.isEmpty(sysDictItem.getItemValue()) && sysDictItem.getItemValue().contains("${")){
                Matcher m = pattern.matcher(sysDictItem.getItemValue());
                List<String> params=new ArrayList<>();
                while(m.find()){
                    params.add(m.group().replace("{","").replace("}",""));
                }
                sysDictItem.setParams(params);
            }
        }
        return sysDictItemList;
    }

    @Override
    public List<SysDictDto> getSysDictItemListByPrefix(String dictCodePrefix) {
        List<SysDictDto> sysDictDtoList = Lists.newCopyOnWriteArrayList();
        List<SysDict> sysDictList = sysDictService.list(new QueryWrapper<SysDict>().likeRight("dict_code", dictCodePrefix));
        SysDictDto sysDictDto;
        for(SysDict sysDict:sysDictList){
            sysDictDto = new SysDictDto();
            BeanUtils.copyProperties(sysDict, sysDictDto);

            List<SysDictItemDto> sysDictItemDtoList = Lists.newCopyOnWriteArrayList();
            List<SysDictItem> sysDictItemList = sysDictItemService.list(new QueryWrapper<SysDictItem>().eq("dict_code", sysDict.getDictCode()));
            SysDictItemDto sysDictItemDto;
            for(SysDictItem sysDictItem:sysDictItemList){
                sysDictItemDto = new SysDictItemDto();
                BeanUtils.copyProperties(sysDictItem, sysDictItemDto);
                sysDictItemDtoList.add(sysDictItemDto);
            }
            sysDictDto.setSysDictItemList(sysDictItemDtoList);
            sysDictDtoList.add(sysDictDto);
        }
        return sysDictDtoList;
    }

}
