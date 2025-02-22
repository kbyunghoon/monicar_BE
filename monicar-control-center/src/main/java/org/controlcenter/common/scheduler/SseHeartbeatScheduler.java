package org.controlcenter.common.scheduler;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import org.controlcenter.alarm.application.AlarmService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
public class SseHeartbeatScheduler {
	private final AlarmService alarmService;

	@Scheduled(fixedRate = 15000)
	public void sendHeartbeat() {
		var emitters = alarmService.getSseEmitters();
		emitters.forEach((userId, emitter) -> {
			try {
				emitter.send(SseEmitter.event()
					.name("ping")
					.data("ping"));
			} catch (IOException e) {
				emitter.complete();
				emitters.remove(userId);
			}
		});
	}
}

