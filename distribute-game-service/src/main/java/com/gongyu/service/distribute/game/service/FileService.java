package com.gongyu.service.distribute.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gongyu.service.distribute.game.model.dto.FileModifyDto;
import com.gongyu.service.distribute.game.model.dto.FileSaveDto;
import com.gongyu.service.distribute.game.model.entity.File;
import com.gongyu.snowcloud.framework.data.mybatis.CrudService;

public interface FileService extends CrudService<File>{

    IPage<File> queryFile(IPage<File> page);

    void saveFile(FileSaveDto fileSaveDto);

    void modifyFile(FileModifyDto fileModifyDto);
}