package org.eventhub.application;

import java.util.Optional;

import org.eventhub.application.port.AlarmRepository;
import org.eventhub.application.port.AlarmSender;
import org.eventhub.domain.Alarm;
import org.eventhub.infrastructure.external.command.AlarmSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlarmService {
	private final AlarmRepository alarmRepository;
	private final AlarmSender alarmSender;
	@Value("${alarm-interval-distance}")
	private Integer alarmIntervalDistance;

	public Optional<Alarm> findById(Long vehicleId) {
		return alarmRepository.findByVehicleId(vehicleId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Optional<Long> saveAlarmIfNecessary(Long vehicleId, Long totalDistance) {
		boolean isNecessary = findById(vehicleId)
			.map(alarm -> checkBiggerThanIntervalDistance(totalDistance, alarm.getDrivingDistance()))
			.orElse(true);

		Optional<Long> alarmId = Optional.empty();
		if (isNecessary) {
			alarmId = Optional.ofNullable(alarmRepository.save(vehicleId));
		}
		return alarmId;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendAlarm(Long alarmId) {
		try {
			alarmSender.sendAlarm(AlarmSend.builder().alarmId(alarmId).build());
		} catch (Exception e) {
			log.info("something wrong: {}", e.getMessage());
		}
	}

	private boolean checkBiggerThanIntervalDistance(long totalDistance, int drivingDistanceLastCheck) {
		return totalDistance - drivingDistanceLastCheck >= alarmIntervalDistance;
	}
}
