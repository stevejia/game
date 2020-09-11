package com.gongyu.service.distribute.game.datalog;

import cn.hutool.db.Db;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础信息
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Getter
public class BasicInfo {
    private static ConcurrentHashMap<String, List<FieldInfo>> fields = new ConcurrentHashMap<>();

    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * mybatis数据底层
     */
    private Model model;
    /**
     * 表名
     */
    private String tbName;
    /**
     * 表注释
     */
    private String tbComment;
    /**
     * 主键名称
     */
    private String pkName;
    /**
     * 主键值
     */
    private String pkValue;

    /**
     * 表字段注释
     */
    private List<FieldInfo> fieldInfos;

    public BasicInfo(DataSource dataSource, Model model, String tbName, String tbComment, String pkName, String pkValue) {
        this.dataSource = dataSource;
        this.model = model;
        this.tbName = tbName;
        this.tbComment = tbComment;
        this.pkName = pkName;
        this.pkValue = pkValue;
    }

    public List<FieldInfo> getFieldInfos() {
        if (!fields.containsKey(this.tbName)) {
            String query = "select column_name fieldName, column_comment comment from information_schema.columns" +
                    " where table_name = \"" + this.tbName + "\" and table_schema = (select database())";
            try {
                this.fieldInfos = Db.use(dataSource).query(query, FieldInfo.class);
            } catch (SQLException e) {
                this.fieldInfos = new ArrayList<>();
            }
            this.fieldInfos.forEach(f -> {
                f.setJFieldName(f.getFieldName());
            });
            fields.put(this.tbName, this.fieldInfos);
        }
        return fields.get(this.tbName);
    }
}