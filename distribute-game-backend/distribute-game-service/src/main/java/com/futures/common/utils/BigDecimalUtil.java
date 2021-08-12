package com.futures.common.utils;


import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/3 20:20
 */
public class BigDecimalUtil {

    /**
     * 指定价格是否在 两个价格之间
     * @param minPrice 最大
     * @param maxPrice 最小
     * @param price
     * @return
     */
    public static boolean compare(BigDecimal minPrice,BigDecimal maxPrice, BigDecimal price){
        return minPrice.compareTo(price) == -1 && maxPrice.compareTo(price) == 1;
    }
}
