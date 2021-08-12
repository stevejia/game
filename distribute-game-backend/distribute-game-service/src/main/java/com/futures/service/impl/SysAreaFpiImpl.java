package com.futures.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futures.model.dto.SysAreaLetterDto;
import com.futures.model.dto.SysAreaStructuredDto;
import com.futures.model.dto.SysAreaUnstructuredDto;
import com.futures.model.entity.SysArea;
import com.futures.service.SysAreaFpi;
import com.futures.service.SysAreaService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SysAreaFpiImpl implements SysAreaFpi {
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SysAreaLetterDto> queryCityByLetterOrder() {
        List<SysAreaLetterDto> sysAreaLetterDtoList = sysAreaService.queryCityByLetterOrder();
        for(SysAreaLetterDto sysAreaLetterDto:sysAreaLetterDtoList){
            String[] strings = PinyinHelper.toHanyuPinyinStringArray(sysAreaLetterDto.getName().charAt(0));
            String firstChar = strings[0].substring(0,1);
            sysAreaLetterDto.setFirstLetter(firstChar.toUpperCase());
        }
        return sysAreaLetterDtoList;
    }

    @Override
    public List<SysAreaUnstructuredDto> queryAreaUnStructured() {
        List<SysAreaUnstructuredDto> sysAreaUnstructuredDtoList;
        Object object = redisTemplate.opsForValue().get("SYS_AREA_UNSTRUCTURED");
        if(null==object){
            sysAreaUnstructuredDtoList = querySysAreaUnStructuredFromDB(null);
            try {
                redisTemplate.opsForValue().set("SYS_AREA_UNSTRUCTURED", JSONObject.toJSONString(sysAreaUnstructuredDtoList));
            }catch (Exception e){
                log.error("redisTemplate set SYS_AREA_UNSTRUCTURED error=" + e.getMessage());
            }
        }else {
            try {
                sysAreaUnstructuredDtoList = JSONObject.parseArray(object.toString(), SysAreaUnstructuredDto.class);
            }catch (Exception e){
                log.error("redisTemplate get SYS_AREA_UNSTRUCTURED error=" + e.getMessage());
                sysAreaUnstructuredDtoList = querySysAreaUnStructuredFromDB(null);
            }
        }
        return sysAreaUnstructuredDtoList;
    }

    @Override
    public List<SysAreaUnstructuredDto> queryAreaByParentId(Long parentId) {
        return querySysAreaUnStructuredFromDB(parentId);
    }

    @Override
    public List<SysAreaStructuredDto> queryAreaStructured() {
        List<SysAreaStructuredDto> sysAreaStructuredDtoList;
        Object object = redisTemplate.opsForValue().get("SYS_AREA_STRUCTURED");
        if(null==object){
            sysAreaStructuredDtoList = querySysAreaStructuredFromDB();
            try {
                redisTemplate.opsForValue().set("SYS_AREA_STRUCTURED", JSONObject.toJSONString(sysAreaStructuredDtoList));
            }catch (Exception e){
                log.error("redisTemplate set SYS_AREA_STRUCTURED error=" + e.getMessage());
            }
        }else {
            try {
                sysAreaStructuredDtoList = JSONObject.parseArray(object.toString(), SysAreaStructuredDto.class);
            }catch (Exception e){
                log.error("redisTemplate get SYS_AREA_STRUCTURED error=" + e.getMessage());
                sysAreaStructuredDtoList = querySysAreaStructuredFromDB();
            }
        }
        return sysAreaStructuredDtoList;
    }

    private List<SysAreaUnstructuredDto> querySysAreaUnStructuredFromDB(Long parentId){
        List<SysAreaUnstructuredDto> sysAreaUnstructuredDtoList = new ArrayList<>();
        QueryWrapper<SysArea> queryWrapper = new QueryWrapper<SysArea>().eq("deleted", 0);
        if(null!=parentId){
            queryWrapper = queryWrapper.eq("parent", parentId);
        }
        queryWrapper = queryWrapper.orderByAsc("parent").orderByAsc("id").orderByAsc("sort");
        List<SysArea> sysAreaList = sysAreaService.list(queryWrapper);
        SysAreaUnstructuredDto SysAreaUnstructuredDto;
        for(SysArea sysArea:sysAreaList){
            SysAreaUnstructuredDto = new SysAreaUnstructuredDto();
            SysAreaUnstructuredDto.setAreaId(String.valueOf(sysArea.getId()));
            SysAreaUnstructuredDto.setName(String.valueOf(sysArea.getName()));
            SysAreaUnstructuredDto.setParentId(String.valueOf(null==sysArea.getParent()?"0":sysArea.getParent()));
            SysAreaUnstructuredDto.setSort(String.valueOf(null==sysArea.getSort()?"0":sysArea.getSort()));
            sysAreaUnstructuredDtoList.add(SysAreaUnstructuredDto);
        }
        return sysAreaUnstructuredDtoList;
    }

    private List<SysAreaStructuredDto> querySysAreaStructuredFromDB(){
        List<SysAreaStructuredDto> provinceAreaList = new ArrayList<>();
        List<SysArea> provinceList = sysAreaService.list(new QueryWrapper<SysArea>().eq("deleted",0).eq("parent", 0).orderByAsc("id").orderByAsc("sort"));
        for(SysArea province:provinceList){
            SysAreaStructuredDto provinceAreaDto = new SysAreaStructuredDto();
            provinceAreaDto.setCode(province.getId());
            provinceAreaDto.setName(province.getName());

            List<SysArea> cityList = sysAreaService.list(new QueryWrapper<SysArea>().eq("deleted",0).eq("parent",province.getId()).orderByAsc("id").orderByAsc("sort"));
            List<SysAreaStructuredDto> cityAreaList = new ArrayList<>();
            for(SysArea city:cityList){
                SysAreaStructuredDto cityAreaDto = new SysAreaStructuredDto();
                cityAreaDto.setCode(city.getId());
                cityAreaDto.setName(city.getName());

                List<SysArea> areaList = sysAreaService.list(new QueryWrapper<SysArea>().eq("deleted",0).eq("parent",city.getId()).orderByAsc("id").orderByAsc("sort"));
                List<SysAreaStructuredDto> areaAreaList = new ArrayList<>();
                for(SysArea area:areaList){
                    SysAreaStructuredDto areaDto = new SysAreaStructuredDto();
                    areaDto.setCode(area.getId());
                    areaDto.setName(area.getName());
                    areaDto.setSub(new ArrayList<>());
                    areaAreaList.add(areaDto);
                }
                cityAreaDto.setSub(areaAreaList);
                cityAreaList.add(cityAreaDto);
            }
            provinceAreaDto.setSub(cityAreaList);
            provinceAreaList.add(provinceAreaDto);
        }
        return provinceAreaList;
    }
}
