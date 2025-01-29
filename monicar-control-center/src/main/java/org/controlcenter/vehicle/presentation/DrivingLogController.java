package org.controlcenter.vehicle.presentation;

import java.time.LocalDate;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.vehicle.application.DrivingLogService;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.controlcenter.vehicle.presentation.dto.DrivingLogResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleDrivingLogDetailsResponse;
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
@RequestMapping("/api/v1/driving-log")
public class DrivingLogController implements DrivingLogApi {
	private final DrivingLogService drivingLogService;

	@GetMapping("/{vehicle-id}")
	public BaseResponse<VehicleDrivingLogDetailsResponse> getDrivingLogByVehicleId(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(required = false) LocalDate start,
		@RequestParam(required = false) LocalDate end
	) {
		return BaseResponse.success(drivingLogService.getVehicleDrivingLogDetails(vehicleId, start, end));
	}

	@GetMapping
	public BaseResponse<PageResponse<DrivingLogResponse>> getDrivingLogList(
		@RequestParam(required = false, defaultValue = "") String keyword,
		@RequestParam(required = false, defaultValue = "CREATED_AT_DESC") VehicleSortType sortType,
		@PageableDefault(size = 8) Pageable pageable
	) {
		Page<DrivingLog> drivingLogPage = drivingLogService.getDrivingLogList(keyword, sortType, pageable);
		Page<DrivingLogResponse> responsePage = drivingLogPage.map(DrivingLogResponse::from);

		return BaseResponse.success(new PageResponse<>(responsePage));
	}
}
