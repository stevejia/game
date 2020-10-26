package com.gongyu.service.distribute.game.utils;

import java.util.List;
import java.util.Set;

import com.gongyu.snowcloud.framework.data.redis.RedisUtils;

public class RedisUtils2 extends RedisUtils {
	public static <T> T getList(String pattern) {
		pattern += "*";
		Set keys = getRedisManager().getRedisTemplate().keys(pattern);
		List<Object> list = getRedisManager().getRedisTemplate().opsForValue().multiGet(keys);
		return (T) list;
	}
	
	public static void removeBatch(String pattern) {
		pattern += "*";
		getRedisManager().removePattern(pattern);
	}
	
}
