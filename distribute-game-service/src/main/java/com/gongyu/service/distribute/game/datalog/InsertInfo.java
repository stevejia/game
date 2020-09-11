package com.gongyu.service.distribute.game.datalog;

import lombok.Getter;

/**
 * 数据插入信息
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Getter
public class InsertInfo extends BaseDataLogHandler {

    /**
     * 插入对象
     */
    private Object insertObj;

    public InsertInfo(BasicInfo basicInfo, Object insertObj) {
        super(basicInfo);
        this.insertObj = insertObj;
    }

}