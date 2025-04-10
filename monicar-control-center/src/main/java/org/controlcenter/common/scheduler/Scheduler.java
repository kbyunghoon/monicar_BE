package org.controlcenter.common.scheduler;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.controlcenter.alarm.application.AlarmService;
import org.controlcenter.alarm.application.port.AlarmRepository;
import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.domain.AlarmCreate;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.domain.SendAlarm;
import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {
	private final AlarmRepository alarmRepository;
	private final VehicleRepository vehicleRepository;
	private final AlarmService alarmService;
	private final ManagerRepository managerRepository;
	private final TaskScheduler taskScheduler;

	@PostConstruct
	public void scheduleTask() {
		scheduleNext();
	}

	private void scheduleNext() {
		long delay = ThreadLocalRandom.current().nextLong(10000, 20001);
		Instant nextExecutionTime = Instant.now().plusMillis(delay);
		taskScheduler.schedule(this::runTask, nextExecutionTime);
	}

	@Transactional
	public void runTask() {
		AlarmStatus[] statuses = AlarmStatus.values();
		AlarmStatus randomStatus = statuses[ThreadLocalRandom.current().nextInt(statuses.length)];

		AlarmCreate alarmCreate;
		if (randomStatus == AlarmStatus.REQUIRED) {
			alarmCreate = AlarmCreate.builder()
				.vehicleId(1L)
				.status(randomStatus)
				.drivingDistance(10000)
				.build();
		} else {
			alarmCreate = AlarmCreate.builder()
				.managerId("1")
				.vehicleId(1L)
				.drivingDistance(10000)
				.status(randomStatus)
				.build();
		}

		var alarm = alarmRepository.save(Alarm.create(alarmCreate));

		VehicleInformation vehicleInformation = vehicleRepository.findById(alarm.getVehicleId())
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_FOUND));

		String managerName = managerRepository.getUserProfile("1").getNickname();

		SendAlarm newAlarm = SendAlarm.builder()
			.id(alarm.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.drivingDistance((alarm.getStatus() == AlarmStatus.COMPLETED || alarm.getStatus() == AlarmStatus.REQUIRED) ?
				vehicleInformation.getSum() : null)
			.status(alarm.getStatus())
			.managerName(managerName)
			.build();

		alarmService.sendAll(newAlarm);
		scheduleNext();
	}
}
