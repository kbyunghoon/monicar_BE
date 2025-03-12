package org.controlcenter.vehicle.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.infrastructure.mybatis.MyBatisVehicleInfoMapper;
import org.controlcenter.vehicle.presentation.RouteResponseWithStatus;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.RouteResponseWithAng;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleQueryRepository {
	private final MyBatisVehicleInfoMapper myBatisVehicleInfoMapper;

	public VehicleInfoResponse getVehicleInfo(VehicleInfoSearchRequest request) {
		return myBatisVehicleInfoMapper.selectVehicleInfo(request.vehicleNumber())
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_FOUND));
	}

	public List<RouteResponseWithAng> getVehicleRouteFrom(
		Long vehicleId,
		LocalDateTime startTime,
		LocalDateTime endTime,
		Integer interval
	) {
		return myBatisVehicleInfoMapper.getVehicleRouteFrom(
			vehicleId,
			startTime,
			endTime,
			interval
		);
	}

	public Page<RouteResponse> getVehicleRouteFromWithPagination(
		Long vehicleId,
		LocalDateTime startTime,
		LocalDateTime endTime,
		Integer interval,
		Pageable pageable
	) {
		int size = pageable.getPageSize();
		int offset = (int) pageable.getOffset();

		List<RouteResponse> routes = myBatisVehicleInfoMapper.getVehicleRouteFromWithPagination(
			vehicleId, startTime, endTime, interval, size, offset
		);

		int totalElements = myBatisVehicleInfoMapper.countVehicleRoutes(
			vehicleId, startTime, endTime, interval
		);

		return new PageImpl<>(routes, pageable, totalElements);
	}

	public List<RouteResponseWithStatus> getRecentRoutesByVehicle(Long vehicleId, LocalDateTime currentTime) {
		return myBatisVehicleInfoMapper.getRecentRoutesByVehicle(
			vehicleId,
			currentTime
		);
	}

	public VehicleModalResponse.RecentVehicleInfo getRecentVehicleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentVehicleInfo(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_ON_YET));
	}

	public VehicleModalResponse.RecentCycleInfo getRecentCycleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentCycleInfo(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_MONITORED_YET));
	}

	public VehicleModalResponse.TodayDrivingHistory getTodayDrivingHistory(Long vehicleId) {
		return myBatisVehicleInfoMapper.getTodayDrivingHistory(vehicleId);
	}

	public String getVehicleStatus(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentVehicleStatus(vehicleId);
	}

	public VehicleModalResponse.VehicleCompanyInfo getVehicleCompanyInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getVehicleCompanyInfo(vehicleId);
	}
}
