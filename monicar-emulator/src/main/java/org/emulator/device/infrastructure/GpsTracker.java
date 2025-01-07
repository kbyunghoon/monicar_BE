package org.emulator.device.infrastructure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.emulator.device.application.port.CycleInfoEventPublisher;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.TransmissionTimeProvider;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.emulator.device.infrastructure.util.MovementCalculator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsTracker implements SensorTracker {
	private final LocationReceiver locationReceiver;
	private final TransmissionTimeProvider timeProvider;
	private final CycleInfoEventPublisher cycleInfoEventPublisher;

	private final Map<String, MovementCalculator> calculators;

	private final Deque<CycleInfo> cycleInfos = new LinkedList<>();
	private CycleInfo recentCycleInfo;

	@Scheduled(initialDelay = 3000, fixedDelay = 1000)
	@Override
	public void track() {
		int time = timeProvider.getTransmissionTime();

		if (cycleInfos.size() > time) {
			List<CycleInfo> cycleInfoList = pollFromDeque(time);

			CycleInfoListCommand message = CycleInfoListCommand.from(cycleInfoList);

			cycleInfoEventPublisher.publishEvent(message);
		}

		GpsTime currentLocation = locationReceiver.getLocation();
		if (recentCycleInfo == null) {
			CycleInfo currentCycleInfo = CycleInfo.create(
				currentLocation,
				GpsStatus.A,
				0,
				0,
				0
			);
			cycleInfos.offerLast(currentCycleInfo);
			recentCycleInfo = currentCycleInfo;
			return;
		}

		CycleInfo currentCycleInfo = CycleInfo.create(
			currentLocation,
			GpsStatus.A,
			getDirection(recentCycleInfo, currentLocation),
			getSpeed(recentCycleInfo, currentLocation),
			getDistance(recentCycleInfo, currentLocation)
		);
		cycleInfos.offerLast(currentCycleInfo);
		recentCycleInfo = currentCycleInfo;

		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "collecting data. . .");
	}

	private List<CycleInfo> pollFromDeque(int size) {
		List<CycleInfo> result = new ArrayList<>();
		while (!cycleInfos.isEmpty() && result.size() < size) {
			CycleInfo cycleInfo = cycleInfos.pollFirst();
			if (Objects.nonNull(cycleInfo)) {
				result.add(cycleInfo);
			}
		}
		return result;
	}

	private int getDirection(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator directionCalculator = calculators.get("directionCalculator");
		return directionCalculator.calculate(preInfo, curInfo);
	}

	private int getSpeed(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator speedCalculator = calculators.get("speedCalculator");
		return speedCalculator.calculate(preInfo, curInfo);
	}

	private int getDistance(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator distanceCalculator = calculators.get("distanceCalculator");
		return distanceCalculator.calculate(preInfo, curInfo);
	}
}
