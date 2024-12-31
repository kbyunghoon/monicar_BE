package org.emulator.device.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.pipe.Gps;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleService {
	private final EmulatorRepository emulatorRepository;
	private final LocationReceiver locationReceiver;
	// private final LocationEventPublisher locationEventPublisher; // 추후 주기 정보를 이벤트로 쏠 때
	private final VehicleCommandSender vehicleCommandSender;

	private static final Deque<CycleInfo> cycleInfos = new LinkedList<>();

	public void onVehicle() {
		Gps onLocation = locationReceiver.getLocation();

		OnInfo onInfo = OnInfo.create(
			LocalDateTime.now(),
			GpsStatus.A,
			onLocation.lat(),
			onLocation.lon(),
			emulatorRepository.getTotalDistance());

		CommonResponse response = vehicleCommandSender.sendOnCommand(onInfo);
	}

	@Scheduled(initialDelay = 2000, fixedDelay = 1000)
	public void trackVehicle() {
		/* 이전 위치 데이터가 꼭 필요한데.. 다 빼가는 방법 말고 다른 방법 찾아보기 */
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
		cycleInfos.add(currentCycleInfo);
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
