package com.futures.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ApiModel
public class PigGoodsSaveDto{
    @ApiModelProperty(value = "级别名称", dataType = "String")
    @NotBlank(message = "名称不能为空")
    private String goodsName;
    @ApiModelProperty(value = "最小价值", dataType = "BigDecimal")
    @NotNull(message = "最小价值不能为空")
    private BigDecimal smallPrice;
    @ApiModelProperty(value = "最大价值", dataType = "BigDecimal")
    @NotNull(message = "最大价值不能为空")
    private BigDecimal largePrice;
    @ApiModelProperty(value = "领养开始时间", dataType = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull(message = "领养时间不能为空")
    private Date startTime;
    @ApiModelProperty(value = "领养结束时间", dataType = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull(message = "领养时间不能为空")
    private Date endTime;
    @ApiModelProperty(value = "领养开始时间", dataType = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date secondStartTime;
    @ApiModelProperty(value = "领养结束时间", dataType = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date secondEndTime;
    @ApiModelProperty(value = "预约数量", dataType = "Integer")
    @NotNull(message = "预约茶籽数量不能为空")
    private Integer reservation;
    @ApiModelProperty(value = "领养（抢）能量", dataType = "Integer")
    @NotNull(message = "领养茶籽数量不能为空")
    private Integer adoptiveEnergy;
    @ApiModelProperty(value = "合约收益天数", dataType = "Integer")
    @NotNull(message = "合约收益天数不能为空")
    private Integer contractDays;
    @ApiModelProperty(value = "合约收益比例", dataType = "BigDecimal")
    @NotNull(message = "合约收益比例不能为空")
    private BigDecimal incomeRatio;
    @ApiModelProperty(value = "可挖PIG数量", dataType = "Integer")
    private Integer pigCurrency;
    @ApiModelProperty(value = "可挖DOGE数量", dataType = "Integer")
    private Integer dogeMoney;
    @ApiModelProperty(value = "图片", dataType = "String")
    @NotBlank(message = "木材图片不能为空")
    private String images;
    @ApiModelProperty(value = "0未开奖1已开奖", dataType = "Integer")
    private Integer todayIsOpen;
    @ApiModelProperty(value = "是否上架，0默认为不上架，1为上架", dataType = "Integer", required = true)
//    @NotNull(message = "是否上架，0默认为不上架，1为上架不能为空")
    private Integer isDisplay;
    @ApiModelProperty(value = "解决不能实时开奖的处理  今天是否锁场次 1锁0开", dataType = "Integer", required = true)
//    @NotNull(message = "解决不能实时开奖的处理  今天是否锁场次 1锁0开不能为空")
    private Integer isLock;
    @ApiModelProperty(value = "", dataType = "Integer", required = true)
//    @NotNull(message = "不能为空")
    private Integer resetTime;
    @ApiModelProperty(value = "重置时间", dataType = "Integer", required = true)
//    @NotNull(message = "重置时间不能为空")
    private Integer gameResetTime;
    @ApiModelProperty(value = "开奖设置类型；0默认一天一次，1一天两次", dataType = "Integer", required = true)
//    @NotNull(message = "开奖设置类型；0默认一天一次，1一天两次不能为空")
    private Integer gameOpenType;
    @ApiModelProperty(value = "是否为合成宠物", dataType = "Integer")
    private Integer isSpell;
    @ApiModelProperty(value = "", dataType = "String")

    private String images2;
    @ApiModelProperty(value = "相同金额的概率", dataType = "Integer")
    @NotNull(message = "相同金额的概率不能为空")
    private Integer probability;
    @ApiModelProperty(value = "金额分类ID", dataType = "Integer")
    private Integer moneyCategoryId;
    @ApiModelProperty(value = "可分裂木材数量", dataType = "Integer")
    private Integer splitNum;
}