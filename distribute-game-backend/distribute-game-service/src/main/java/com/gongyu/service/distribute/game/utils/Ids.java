package com.gongyu.service.distribute.game.utils;

import java.security.SecureRandom;
import java.util.UUID;

public abstract class Ids {
    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成,中间有-分割
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return random.nextLong();
    }

}
