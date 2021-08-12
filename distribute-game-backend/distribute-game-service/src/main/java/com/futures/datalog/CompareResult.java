package com.futures.datalog;

import lombok.Data;

/**
 * 对比两个对象结果
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Data
public class CompareResult {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段注释
     */
    private String fieldComment;
    /**
     * 字段旧值
     */
    private Object oldValue;
    /**
     * 字段新值
     */
    private Object newValue;
}