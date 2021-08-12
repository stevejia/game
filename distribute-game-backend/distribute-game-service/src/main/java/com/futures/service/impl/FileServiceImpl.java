package com.futures.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.mapper.FileMapper;
import com.futures.model.dto.FileModifyDto;
import com.futures.model.dto.FileSaveDto;
import com.futures.model.entity.File;
import com.futures.service.FileService;
import com.gongyu.snowcloud.framework.data.mybatis.CrudServiceSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileServiceImpl extends CrudServiceSupport<FileMapper, File> implements FileService  {

    @Override
    public IPage<File> queryFile(IPage<File> page) {
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveFile(FileSaveDto fileSaveDto) {
        File file = new File();
        BeanUtils.copyProperties(fileSaveDto, file);
        this.save(file);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void modifyFile(FileModifyDto fileModifyDto) {
        File file = new File();
        BeanUtils.copyProperties(fileModifyDto, file);
        this.updateById(file);
    }
}