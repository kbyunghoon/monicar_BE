package org.controlcenter.vehicle.application;

import org.controlcenter.common.response.PageResponse;
import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DrivingLogService {
	private final DrivingLogRepository drivingLogRepository;

	@Transactional
	public Page<DrivingLog> getDrivingLogList(String vehicleNumber, Pageable pageable) {
		return drivingLogRepository.findByVehicleNumber(vehicleNumber, pageable);
	}
}
