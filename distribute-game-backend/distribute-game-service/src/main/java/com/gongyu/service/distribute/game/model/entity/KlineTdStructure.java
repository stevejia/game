package com.gongyu.service.distribute.game.model.entity;

import com.gongyu.service.distribute.game.task.KlineTask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KlineTdStructure {

	/**
	 * 唯一标识 id
	 */
	private Integer id;

	/**
	 * TD结构开始k线所在索引
	 */
	private Integer index;

	/**
	 * 第一根k线的klineTime;
	 */
	private String klineTime;

	/**
	 * 合约id
	 */
	private String instrumentId;

	/**
	 * K线周期
	 */
	private Integer period;

	/**
	 * 是否熊市 false 熊市反转(买入结构) true 牛市反转(卖出结构)
	 */
	private boolean isReversal;

	/**
	 * 合约k线时间 标识当前结构包含的k线列表 以","分隔
	 */
	private String klineTimes;

	/**
	 * 当前TD结构是否结束 用于后续动态计算获取StartIndex
	 */
	private boolean isStructureComplete;

	/**
	 * TD结构是否完善
	 */
	private boolean isPerfect;

	/**
	 * TD结构完善是否结束 涉及到后续动态计算 开始索引的问题
	 */
	private boolean isPerfectComplete;

	/**
	 * 是否满足开仓条件
	 */
	private boolean isSatisfyOpen;
}
