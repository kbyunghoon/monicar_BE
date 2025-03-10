package org.eventhub.domain;

import lombok.RequiredArgsConstructor;

import org.eventhub.application.VehicleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ScheduledTask {
	private final VehicleService vehicleService;

	/* vehicle_information의 운행 일수를 매일 정시에 업데이트합니다 */
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateDrivingDaysAll() {
		vehicleService.updateDrivingDaysAll();
	}
}
