package com.futures.datalog;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 数据更新拦截器
 *
 * @author chenyong
 * @date 2019/12/31
 */
@AllArgsConstructor
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class DataUpdateInterceptor implements Interceptor {

    private final DataSource dataSource;
    private final DataLogHandler dataLogHandler;

    @Override
    public Object intercept(Invocation invocation) {
        Object result = null;
        try {
            this.dealData(invocation);
            result = invocation.proceed();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 对数据库操作传入参数进行处理
     * @param invocation
     */
    public void dealData(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        // 参数
        Object et = args[1];
        if (et instanceof Model) {
            this.doLog(mappedStatement, et);
        } else if (et instanceof Map) {
            if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
                this.doLog(mappedStatement, et);
            } else {
                String key = "et";
                String listKey = "collection";
                if (((Map) et).containsKey(key) && ((Map) et).get(key) instanceof Model) {
                    this.doLog(mappedStatement, ((Map) et).get(key));
                } else if (((Map) et).containsKey(listKey) && ((Map) et).get(listKey) instanceof Collection) {
                    List<Object> list = (List<Object>) ((Map) et).get(listKey);
                    for (Object obj : list) {
                        if (obj instanceof Model) {
                            this.doLog(mappedStatement, obj);
                        }
                    }
                }
            }

        }
    }

    /**
     * 根据不同参数及操作进行不同的日志记录
     * @param mappedStatement
     * @param et
     */
    public void doLog(MappedStatement mappedStatement, Object et) {
        try {
            if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
                if(et instanceof Map){
                    String className = mappedStatement.getParameterMap().getType().getName();
                    className = className.substring(className.lastIndexOf(".")+1);
                    et = ((Map) et).get(lowerFirst(className));
                } else {
                    return;
                }
            }
            // 反射获取实体类
            Class<?> clazz = et.getClass();
            // 不含有表名的实体就默认通过
            if (!clazz.isAnnotationPresent(TableName.class)) {
                return;
            }
            // 获取表名
            TableName tableName = clazz.getAnnotation(TableName.class);
            String tbName = tableName.value();
            if (StringUtils.isBlank(tbName)) {
                return;
            }

            TableLog tableLog = clazz.getAnnotation(TableLog.class);
            if(null==tableLog){
                return;
            }
            String tbLogName = tableLog.value();
            if (StringUtils.isBlank(tbLogName)) {
                return;
            }

            String pkName = null;
            String pkValue = null;
            // 获取实体所有字段
            List<Field> fields = new ArrayList<>() ;
            Class tempClass = clazz;
            while (tempClass != null) {
                fields.addAll(Arrays.asList(tempClass .getDeclaredFields()));
                tempClass = tempClass.getSuperclass();
            }
            for (Field field : fields) {
                // 设置些属性是可以访问的
                field.setAccessible(true);
                if (field.isAnnotationPresent(TableId.class)) {
                    // 获取主键
                    pkName = field.getName();
                    try {
                        // 获取主键值
                        pkValue = field.get(et).toString();
                    } catch (Exception e) {
                        pkValue = null;
                    }

                }
            }
            BasicInfo basicInfo = new BasicInfo(dataSource, (Model) et, tbName, tbLogName, pkName, pkValue);

            // 插入
            if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
                InsertInfo insertInfo = new InsertInfo(basicInfo, et);
                dataLogHandler.insertHandler(insertInfo);
            }
            // 更新
            if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType()) && StringUtils.isNotBlank(pkName) && StringUtils.isNotBlank(pkValue)) {
                Object oldObj = this.queryData(pkName, pkValue, (Model) et);
                if (oldObj != null) {
                    UpdateInfo updateInfo = new UpdateInfo(basicInfo, oldObj, et);
                    // 调用自定义处理方法
                    dataLogHandler.updateHandler(updateInfo);
                }
            }
            // 删除
            if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType()) && StringUtils.isNotBlank(pkName) && StringUtils.isNotBlank(pkValue)) {
                Object delObj = this.queryData(pkName, pkValue, (Model) et);
                if (delObj != null) {
                    DeleteInfo deleteInfo = new DeleteInfo(basicInfo, delObj);
                    // 调用自定义处理方法
                    dataLogHandler.deleteHandler(deleteInfo);
                }
            }
        }catch (Exception e){
            log.error("doLog error=" + e.getMessage(), e);
        }
    }

    /**
     * 根据主键和主键值查询数据
     * @param pkName
     * @param pkValue
     * @param clazz
     * @return
     */
    private Object queryData(String pkName, String pkValue, Model clazz) {
        // 查询更新前数据
        return clazz.selectOne(Wrappers.query().eq(pkName, pkValue));
    }

    private String lowerFirst(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}