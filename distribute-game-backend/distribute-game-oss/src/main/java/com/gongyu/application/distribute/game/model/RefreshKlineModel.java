package com.gongyu.application.distribute.game.model;

import java.util.List;

import com.gongyu.service.distribute.game.model.dto.KlineDto;
import com.gongyu.service.distribute.game.model.entity.KlineTdStructure;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshKlineModel {
	private List<KlineDto> klines;
	
	private List<KlineTdStructure> tdStructures;
}
