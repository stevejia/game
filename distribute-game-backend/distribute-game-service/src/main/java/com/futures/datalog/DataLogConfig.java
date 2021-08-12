package com.futures.datalog;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据更新日志处理配置（实现按需加载）
 *
 * @author chenyong
 * @date 2019/12/31
 */
@Configuration
@AllArgsConstructor
@ConditionalOnBean({DataSource.class, DataLogHandler.class})
public class DataLogConfig {

    private final DataLogHandler dataLogHandler;
    private final DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean
    public DataUpdateInterceptor dataUpdateInterceptor() {
        return new DataUpdateInterceptor(dataSource, dataLogHandler);
    }
}