package com.gongyu.service.distribute.game.model.entity;

import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 * WARNING:
 * this code is generated by code-generator
 * please DO NOT modify this file
 * @author code-generator
 * @date 2020-06-02 19:51:54
 */
@Data
public class UserExclusivePigDel extends BaseEntity {
  private Long delid;
  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 当前对应的订单id
   */
  private Long orderId;
  /**
   * 猪等级
   */
  private Long pigId;
  /**
   * 是否可出售,默认0不可出售，1可出售
   */
  private Integer isAbleSale;
  /**
   * 金额
   */
  private BigDecimal price;
  /**
   * 收购人ID
   */
  private Long fromUserId;
  /**
   * 指定用户ID
   */
  private Long appointUserId;
  /**
   * 买入时间
   */
  private Long buyTime;
  private Long endTime;
  private String type;
  private String deltime;

}
