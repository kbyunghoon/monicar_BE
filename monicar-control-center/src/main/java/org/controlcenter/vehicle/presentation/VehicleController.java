package org.controlcenter.vehicle.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.vehicle.application.VehicleService;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController {
	private final VehicleQueryRepository vehicleQueryRepository;
	private final VehicleService vehicleService;

	@GetMapping
	public BaseResponse<VehicleInfoResponse> getVehicleInfo(
		@Valid VehicleInfoSearchRequest request
	) {
		var vehicleInfoResponse = vehicleQueryRepository.getVehicleInfo(request);
		return BaseResponse.success(vehicleInfoResponse);
	}

	@GetMapping("/{vehicle-id}/routes")
	public BaseResponse<VehicleRouteResponse> getVehicleRoute(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "startTime") LocalDateTime startTime,
		@RequestParam(value = "endTime") LocalDateTime endTime,
		@RequestParam(value = "interval", defaultValue = "60") Integer interval
	) {
		String vehicleNumber = vehicleService.getVehicleNumber(vehicleId);

		List<RouteResponse> routesResponses = vehicleQueryRepository.getVehicleRouteFrom(vehicleId, startTime, endTime, interval);

		VehicleRouteResponse response = VehicleRouteResponse.builder()
			.vehicleNumber(vehicleNumber)
			.routes(routesResponses)
			.build();
		return BaseResponse.success(response);
	}
}
