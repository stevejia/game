package com.gongyu.service.distribute.game.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KlineOpenPosition {
	private Integer id;
	private String openTime;
	private String commodityName;
	private String instrumentId;
	private Integer period;
	private Integer openType;
	private Double openPrice;
	private Double stopLossPrice;
}
