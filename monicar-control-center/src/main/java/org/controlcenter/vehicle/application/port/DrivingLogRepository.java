package org.controlcenter.vehicle.application.port;

import java.time.LocalDate;
import java.util.List;

import org.controlcenter.vehicle.domain.DailyDrivingSummary;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.HourlyDrivingLogs;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrivingLogRepository {
	List<DailyDrivingSummary> getDailySummaries(Long vehicleId, LocalDate start, LocalDate end);

	List<HourlyDrivingLogs> getHourlyDrivingLogs(Long vehicleId, LocalDate targetDate);

	Page<DrivingLog> findByVehicleNumber(String vehicleNumber, VehicleSortType sortType, Pageable pageable);

	VehicleHeaderInfo findVehicleHeaderInfoByVehicleId(Long vehicleId);

	List<DrivingLogDetailsContent> findDrivingLogsByVehicleIdAndDateRange(
		Long vehicleId,
		LocalDate startDate,
		LocalDate endDate);

	Integer sumByVehicleIdAndDateRange(Long vehicleId, LocalDate startDate, LocalDate endDate);
}