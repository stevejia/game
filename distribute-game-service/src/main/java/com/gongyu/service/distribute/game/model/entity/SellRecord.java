package com.gongyu.service.distribute.game.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.gongyu.snowcloud.framework.data.mybatis.BaseEntity;
import lombok.Data;

/**
 * 
 * WARNING:
 * this code is generated by code-generator
 * please DO NOT modify this file
 * @author code-generator
 * @date 2020-06-22 10:32:30
 */
@Data
public class SellRecord extends BaseEntity {
  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 出售收益
   */
  private BigDecimal sellIncom;
  /**
   * 对应pig等级
   */
  private Long pigLevel;
  /**
   * 出售日期
   */
  private Date sellDate;
  /**
   * 生成开奖精灵：0未生成  1已生成
   */
  private Integer status;

}
