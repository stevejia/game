package com.futures.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/19 17:21
 */
@Data
@ApiModel
public class AccountStoreDto {

    @ApiModelProperty(value = "总茶籽", dataType = "String")
    private String totalStore;
    @ApiModelProperty(value = "茶籽流水", dataType = "List")
    private List<AccountStoreDetailDto> storeList;
}
