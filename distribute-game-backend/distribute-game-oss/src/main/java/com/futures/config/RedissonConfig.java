package com.futures.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 20:35
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public Redisson redissonClient(){
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
        config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
        config.useSingleServer().setDatabase(3);
        return (Redisson) Redisson.create(config);
    }
}
