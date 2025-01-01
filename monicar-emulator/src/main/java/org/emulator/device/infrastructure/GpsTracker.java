package org.emulator.device.infrastructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;

import java.util.LinkedList;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.pipe.Gps;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsTracker implements SensorTracker{
	private final LocationReceiver locationReceiver;
	private final Deque<CycleInfo> cycleInfos = new LinkedList<>();

	@Scheduled(initialDelay = 3000, fixedDelay = 1000)
	@Override
	public void track() {
		if (cycleInfos.size() > 10) {
			List<CycleInfo> sending = pollFromDeque(10);
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
			270,
			100,
			10000,
			100
		);
		cycleInfos.offerLast(currentCycleInfo);
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "waiting..");
	}

	public List<CycleInfo> pollFromDeque(int size) {
		List<CycleInfo> result = new ArrayList<>();
		for (int i = 0; i < size && !cycleInfos.isEmpty(); i++) {
			result.add(cycleInfos.pollFirst()); // pollFirst()로 맨 앞에서부터 제거
		}
		return result;
	}
}
