package org.emulator.device.application;

import org.emulator.device.application.port.GpsSseSender;
import org.emulator.device.domain.CycleInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseSendWithKeepAliveEmitter implements GpsSseSender {
	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public SseEmitter streamGpsTransmissionStatus() {
		SseEmitter emitter = new SseEmitter(60 * 1000L);
		emitters.add(emitter);

		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));

		return emitter;
	}

	@Override
	public void sendGpsTransmissionStatus(String message) {
		sendEvent("gpsStatus", message);
	}

	@Override
	public void sendGps(CycleInfo cycleInfo) {
		sendEvent("gpsData", cycleInfo.getGeo().toString());
	}

	private void sendEvent(String eventType, Object data) {
		for (SseEmitter emitter : emitters) {
			try {
				emitter.send(SseEmitter.event().name(eventType).data(data));
			} catch (Exception e) {
				emitter.complete();
				emitters.remove(emitter);
			}
		}
	}
}
