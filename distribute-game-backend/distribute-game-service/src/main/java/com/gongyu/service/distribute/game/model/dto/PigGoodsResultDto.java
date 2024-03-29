package com.gongyu.service.distribute.game.model.dto;

import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/28 15:48
 */
@Data
public class PigGoodsResultDto extends BaseEntity implements Comparable<PigGoodsResultDto> {

    @ApiModelProperty(value = "级别名称", dataType = "String")
    private String goodsName;
    @ApiModelProperty(value = "最小价值", dataType = "BigDecimal")
    private BigDecimal smallPrice;
    @ApiModelProperty(value = "最大价值", dataType = "BigDecimal")
    private BigDecimal largePrice;
    @ApiModelProperty(value = "领养开始时间", dataType = "Date")
//    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date startTime;
    @ApiModelProperty(value = "领养结束时间", dataType = "Date")
//    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date endTime;
    @ApiModelProperty(value = "领养开始时间", dataType = "Date")
    private Date secondStartTime;
    @ApiModelProperty(value = "领养结束时间", dataType = "Date")
    private Date secondEndTime;
    @ApiModelProperty(value = "预约数量", dataType = "Integer")
    private Integer reservation;
    @ApiModelProperty(value = "领养（抢）能量", dataType = "Integer")
    private Integer adoptiveEnergy;
    @ApiModelProperty(value = "合约收益天数", dataType = "Integer")
    private Integer contractDays;
    @ApiModelProperty(value = "合约收益比例", dataType = "BigDecimal")
    private BigDecimal incomeRatio;
    @ApiModelProperty(value = "可挖PIG数量", dataType = "Integer")
    private Integer pigCurrency;
    @ApiModelProperty(value = "可挖DOGE数量", dataType = "Integer")
    private Integer dogeMoney;
    @ApiModelProperty(value = "图片", dataType = "String")
    private String images;
    @ApiModelProperty(value = "0未开奖1已开奖", dataType = "Integer")
    private Integer todayIsOpen;
    @ApiModelProperty(value = "是否上架，0默认为不上架，1为上架", dataType = "Integer")
    private Integer isDisplay;
    @ApiModelProperty(value = "解决不能实时开奖的处理  今天是否锁场次 1锁0开", dataType = "Integer")
    private Integer isLock;
    @ApiModelProperty(value = "", dataType = "Integer")
    private Integer resetTime;
    @ApiModelProperty(value = "重置时间", dataType = "Integer")
    private Integer gameResetTime;
    @ApiModelProperty(value = "开奖设置类型；0默认一天一次，1一天两次", dataType = "Integer")
    private Integer gameOpenType;
    @ApiModelProperty(value = "是否为合成宠物", dataType = "Integer")
    private Integer isSpell;
    @ApiModelProperty(value = "", dataType = "String")
    private String images2;
    @ApiModelProperty(value = "相同金额的概率", dataType = "Integer")
    private Integer probability;
    @ApiModelProperty(value = "金额分类ID", dataType = "Integer")
    private Integer moneyCategoryId;
    @ApiModelProperty(value = "可分裂精灵数量", dataType = "Integer")
    private Integer splitNum;

    @ApiModelProperty(value = "状态：1 修炼中，2 预约，3 抢购 4 已预约 5 暂未开放 6 待领养")
    private Integer pigStatus;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "选中策略值 优先选中最大值",dataType = "Long")
    private Long openms;

    @Override
    public int compareTo(PigGoodsResultDto o) {
        if(openms > o.getOpenms()){
            return 1;
        }else if(openms < o.getOpenms()){
            return -1;
        }else{
            return 0;
        }
    }
}
