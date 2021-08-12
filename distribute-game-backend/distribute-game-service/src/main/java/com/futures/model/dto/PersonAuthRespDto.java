package com.futures.model.dto;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/7/8 11:57
 */
@Data
public class PersonAuthRespDto {

//


    /**
     * 0：成功，非0：失败
     */
    private String code;

    private String message;

    //附加参数
    private AuthPersonResultDto result;


    public boolean checkRespStatus(){
        return code.equals("0");
    }
}
