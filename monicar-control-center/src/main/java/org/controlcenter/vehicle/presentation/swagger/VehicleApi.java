package org.controlcenter.vehicle.presentation.swagger;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.vehicle.presentation.dto.GeoClusteringResponse;
import org.controlcenter.vehicle.presentation.dto.GeoCoordinateDetailsResponse;
import org.controlcenter.vehicle.presentation.dto.KeyOnRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleLocationResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "차량 정보 API", description = "차량 정보 관련 API")
public interface VehicleApi {

	@Operation(summary = "차량 정보 조회", description = "차량 고유 ID를 사용하여 차량 기본 정보를 조회")
	BaseResponse<VehicleInfoResponse> getVehicleInfo(
		@Valid VehicleInfoSearchRequest request
	);

	@Operation(summary = "차량 상세 모달 정보 조회", description = "차량 고유 ID를 사용하여 차량 상세 정보 모달 정보를 조회")
	BaseResponse<VehicleModalResponse> getVehicleInfo(
		@PathVariable(name = "vehicle-id") Long vehicleId
	);

	@Operation(summary = "차량 번호를 기반으로 최신 정보를 조회하는 API", description = "차량 고유 번호를 사용하여 최신 정보를 조회")
	BaseResponse<VehicleLocationResponse> getVehicleInfo(
		@RequestParam("vehicle-number") String vehicleNumber
	);

	@Operation(summary = "개별 차량 경로 조회", description = "차량 고유 ID를 통해 특정 시간 안에 일정한 간격의 경로 정보를 조회")
	BaseResponse<VehicleRouteResponse> getVehicleRoute(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "startTime") LocalDateTime startTime,
		@RequestParam(value = "endTime") LocalDateTime endTime,
		@RequestParam(value = "interval", defaultValue = "60") Integer interval
	);

	@Operation(summary = "지도 클러스터링 조회", description = "특정 회사의 모든 차량 지도 클러스터링을 조회")
	BaseResponse<List<GeoClusteringResponse>> clusterCoordinate(
		@RequestParam(value = "level") Integer level,
		@RequestParam(value = "neLat") Integer neLat,
		@RequestParam(value = "neLng") Integer neLng,
		@RequestParam(value = "swLat") Integer swLat,
		@RequestParam(value = "swLng") Integer swLng
	);

	@Operation(summary = "지도 클러스터링 상세 조회", description = "클러스터링한 차량 개수가 적다면 상세 조회 한다")
	BaseResponse<List<GeoCoordinateDetailsResponse>> clusterCoordinateDetail(
		@RequestParam(value = "level") Integer level,
		@RequestParam(value = "neLat") Integer neLat,
		@RequestParam(value = "neLng") Integer neLng,
		@RequestParam(value = "swLat") Integer swLat,
		@RequestParam(value = "swLng") Integer swLng
	);

	@Operation(summary = "시동 여부에 따른 전체 차량 수 조회", description = "특정 회사의 시동 여부에 따른 전체 차량 수 조회")
	BaseResponse<VehicleEngineStatusResponse> getVehicleEngineStatus();
}
