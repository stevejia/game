package com.gongyu.service.distribute.game.event;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class KlineTaskEvent {
	private String instrumentId;
	private Integer period;

	@Scheduled(fixedRate=3000)
	public void run() {
		log.info("task run");
	}
}
