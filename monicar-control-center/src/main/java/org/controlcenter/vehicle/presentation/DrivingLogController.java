package org.controlcenter.vehicle.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.vehicle.application.DrivingLogService;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.presentation.dto.DrivingLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/driving-log")
public class DrivingLogController {
	private final DrivingLogService drivingLogService;

	@GetMapping
	public BaseResponse<PageResponse<DrivingLogResponse>> getDrivingLogList(
		@RequestParam(required = false, defaultValue = "") String keyword,
		@PageableDefault(size = 10) Pageable pageable
	) {
		Page<DrivingLog> drivingLogPage = drivingLogService.getDrivingLogList(keyword, pageable);
		Page<DrivingLogResponse> responsePage = drivingLogPage.map(DrivingLogResponse::from);

		return BaseResponse.success(new PageResponse<>(responsePage));
	}
}
