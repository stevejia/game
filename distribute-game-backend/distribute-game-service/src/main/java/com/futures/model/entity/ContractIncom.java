package com.futures.model.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/24 18:37
 */
@Data
public class ContractIncom {

    /**
     * 用户
     */
    private Users users;

    /**
     * 合约收入
     */
    private BigDecimal contractIncom;



}
