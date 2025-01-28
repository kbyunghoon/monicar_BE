package org.controlcenter.vehicle.presentation.swagger;

import java.time.LocalDate;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.controlcenter.vehicle.presentation.dto.DrivingLogResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleDrivingLogDetailsResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleTypeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "운행일지 API", description = "운행일지 관련 API")
public interface DrivingLogApi {

	@Operation(summary = "운행일지 상세 조회", description = "차량 고유 ID를 사용하여 운행일지 기본 정보를 조회")
	BaseResponse<VehicleDrivingLogDetailsResponse> getDrivingLogByVehicleId(
		@Parameter(name = "vehicle-id", description = "차량 고유 ID", required = true, in = ParameterIn.PATH)
		@PathVariable("vehicle-id") Long vehicleId,

		@Parameter(name = "start", description = "조회 시작 날짜(기본 값 1년 전) YYYY-MM-DD", required = false)
		@RequestParam(required = false) LocalDate start,

		@Parameter(name = "end", description = "조회 종료 날짜(기본 값 현재) YYYY-MM-DD", required = false)
		@RequestParam(required = false) LocalDate end
	);

	@Operation(summary = "차량 등록 시 차종 요청", description = "차량 등록 시 차종 조회")
	BaseResponse<List<VehicleTypeResponse>> requestVehicleTypes();

	@Operation(summary = "운행일지 기본 조회", description = "키워드 및 페이징을 사용하여 운행일지 목록을 조회합니다.")
	@Parameter(name = "keyword", description = "차량번호 검색 키워드", required = false, example = "")
	@Parameter(name = "size", description = "페이지당 보여질 개수(default: 8)", required = false)
	@Parameter(name = "page", description = "요청할 페이지(default: 1)", required = false)
	@Parameter(name = "pageable", hidden = true)
	BaseResponse<PageResponse<DrivingLogResponse>> getDrivingLogList(
		@RequestParam(required = false, defaultValue = "") String keyword,
		@Parameter(name = "sort", description = "정렬 순서(default: 등록일 최신순)", required = false)
		@RequestParam(required = false, defaultValue = "VEHICLE_NUMBER_ASC") VehicleSortType sortType,
		@PageableDefault(size = 10) Pageable pageable
	);
}
