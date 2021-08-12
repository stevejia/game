package com.futures.datalog;

/**
 * 数据日志处理
 *
 * @author chenyong
 * @date 2019/12/31
 */
public interface DataLogHandler {

    /**
     * 插入处理
     *
     * @param insertInfo 插入数据信息
     */
    void insertHandler(InsertInfo insertInfo);

    /**
     * 更新处理
     *
     * @param updateInfo 更新数据信息
     */
    void updateHandler(UpdateInfo updateInfo);

    /**
     * 删除处理
     *
     * @param deleteInfo 删除数据信息
     */
    void deleteHandler(DeleteInfo deleteInfo);
}