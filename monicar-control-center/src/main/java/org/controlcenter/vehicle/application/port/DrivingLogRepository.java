package org.controlcenter.vehicle.application.port;

import org.controlcenter.vehicle.domain.DrivingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrivingLogRepository {
	Page<DrivingLog> findByVehicleNumber(String vehicleNumber, Pageable pageable);
}