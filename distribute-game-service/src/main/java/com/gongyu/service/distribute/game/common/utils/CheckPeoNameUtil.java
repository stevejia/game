package com.gongyu.service.distribute.game.common.utils;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/23 17:22
 */
public class CheckPeoNameUtil {

    public static Boolean validateName(String name) {
        return name.matches("^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$");
    }

}
