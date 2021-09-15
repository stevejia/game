package com.gongyu.application.distribute.game.model;

import lombok.Data;

@Data
public class KlineModel {

	private String klineTime;

	private Double openprice;

	private Double closeprice;

	private Double highestprice;

	private Double lowestprice;

}
