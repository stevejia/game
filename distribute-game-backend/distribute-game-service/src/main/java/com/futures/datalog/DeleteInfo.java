package com.futures.datalog;

import lombok.Getter;

/**
 * 数据删除信息
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Getter
public class DeleteInfo extends BaseDataLogHandler {

    /**
     * 删除对象
     */
    private Object deleteObj;

    public DeleteInfo(BasicInfo basicInfo, Object deleteObj) {
        super(basicInfo);
        this.deleteObj = deleteObj;
    }
}