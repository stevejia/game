package com.futures.model.dto;

import lombok.Data;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 14:36
 */
@Data
public class AuthPersonResultDto {

    //    {
//        "code": "0", //返回码，0：成功，非0：失败（详见错误码定义）
//            //当code=0时，再判断下面result中的res；当code!=0时，表示调用已失败，无需再继续
//            "message": "成功", //返回码说明
//            "result": {
//        "name": "冯天", //姓名
//                "idcard": "350301198011129422", //身份证号
//                "res": "1", //核验结果状态码，1 一致；2 不一致；3 无记录
//                "description": "一致",  //核验结果状态描述
//                "sex": "男",
//                "birthday": "19940320",
//                "address": "江西省南昌市东湖区"
//    }
//    }

    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 核验结果状态码 1 一致；2 不一致；3 无记录
     */
    private String res;
    /**
     * 核验结果状态描述
     */
    private String description;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日 yyyymmdd
     */
    private String birthday;
    /**
     * 地址
     */
    private String address;
}
