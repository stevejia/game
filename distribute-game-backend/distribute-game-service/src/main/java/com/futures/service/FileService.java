package com.futures.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futures.model.dto.FileModifyDto;
import com.futures.model.dto.FileSaveDto;
import com.futures.model.entity.File;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface FileService extends CrudService<File>{

    IPage<File> queryFile(IPage<File> page);

    void saveFile(FileSaveDto fileSaveDto);

    void modifyFile(FileModifyDto fileModifyDto);
}