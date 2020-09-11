package com.gongyu.service.distribute.game.datalog;

import lombok.Data;

/**
 * 字段信息
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Data
public class FieldInfo {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * java字段名
     */
    private String jFieldName;
    /**
     * 注释
     */
    private String comment;
}