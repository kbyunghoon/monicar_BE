package org.controlcenter.vehicle.presentation;

import java.time.LocalDate;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.vehicle.application.DrivingLogService;
import org.controlcenter.vehicle.application.port.VehicleTypeRepository;
import org.controlcenter.vehicle.domain.DailyDrivingSummary;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.HourlyDrivingLogs;
import org.controlcenter.vehicle.domain.Period;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.controlcenter.vehicle.presentation.dto.DrivingLogResponse;
import org.controlcenter.vehicle.presentation.dto.HourlyDrivingLogsResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleDrivingLogDetailsResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleTypeResponse;
import org.controlcenter.vehicle.presentation.swagger.DrivingLogApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/log")
public class DrivingLogController implements DrivingLogApi {
	private final DrivingLogService drivingLogService;
	private final VehicleTypeRepository vehicleTypeRepository;

	@Override
	@GetMapping("/{vehicle-id}")
	public BaseResponse<VehicleDrivingLogDetailsResponse> getDrivingLogByVehicleId(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(required = false) LocalDate start,
		@RequestParam(required = false) LocalDate end
	) {
		return BaseResponse.success(drivingLogService.getVehicleDrivingLogDetails(vehicleId, start, end));
	}

	@Override
	@GetMapping
	public BaseResponse<PageResponse<DrivingLogResponse>> getDrivingLogList(
		@RequestParam(required = false, defaultValue = "") String keyword,
		@RequestParam(required = false, defaultValue = "CREATED_AT_DESC") VehicleSortType sort,
		@PageableDefault(size = 8) Pageable pageable
	) {
		Page<DrivingLog> drivingLogPage = drivingLogService.getDrivingLogList(keyword, sort, pageable);
		Page<DrivingLogResponse> responsePage = drivingLogPage.map(DrivingLogResponse::from);

		return BaseResponse.success(new PageResponse<>(responsePage));
	}

	@Override
	@GetMapping("/vehicle-type")
	public BaseResponse<List<VehicleTypeResponse>> requestVehicleTypes() {
		List<VehicleTypeResponse> vehicleTypes = vehicleTypeRepository.findAll()
			.stream()
			.map(VehicleTypeResponse::from)
			.toList();

		return BaseResponse.success(vehicleTypes);
	}

	@Override
	@GetMapping("/daily/{vehicle-id}")
	public BaseResponse<List<DailyDrivingSummary>> getDailyDrivingSummary(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(required = false, defaultValue = "WEEK") Period period
	) {
		return BaseResponse.success(drivingLogService.getDailySummaries(vehicleId, period));
	}

	@Override
	@GetMapping("/hourly/{vehicle-id}")
	public BaseResponse<HourlyDrivingLogsResponse> getDailyDrivingSummary(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam LocalDate date
	) {
		return BaseResponse.success(drivingLogService.getHourlySummaries(vehicleId, date));
	}
}
