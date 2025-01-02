package org.emulator.device.infrastructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;

import java.util.LinkedList;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.TransmissionTimeProvider;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.pipe.Gps;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsTracker implements SensorTracker {
	private final LocationReceiver locationReceiver;
	@Qualifier("emulatorTransmissionTimeProvider")
	private final TransmissionTimeProvider timeProvider;

	private final Deque<CycleInfo> cycleInfos = new LinkedList<>();

	@Scheduled(initialDelay = 3000, fixedDelay = 1000)
	@Override
	public void track() {
		int time = timeProvider.getTransmissionTime();

		if (cycleInfos.size() > time) {
			List<CycleInfo> sending = pollFromDeque(time);
			// vehicleCommandSender.sendCycleCommand(sending);
			log.info("[Thread: {}] {}", Thread.currentThread().getName(), "sending!");
		}
		Gps location = locationReceiver.getLocation();
		CycleInfo previousCycleInfo = cycleInfos.peekLast();
		// 방향 연산
		// 속도 연산
		// 누적 거리 연산
		CycleInfo currentCycleInfo = CycleInfo.create(
			LocalDateTime.now(),
			GpsStatus.A,
			location.lat(),
			location.lon(),
			270, // 방향 연산 값
			100, // 속도 연산 값
			10000, // 누적 거리 연산 값
			100 // 배터리
		);
		cycleInfos.offerLast(currentCycleInfo);
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "waiting..");
	}

	public List<CycleInfo> pollFromDeque(int size) {
		List<CycleInfo> result = new ArrayList<>();
		while (!cycleInfos.isEmpty() && result.size() < size) {
			result.add(cycleInfos.pollFirst());
		}
		return result;
	}
}
