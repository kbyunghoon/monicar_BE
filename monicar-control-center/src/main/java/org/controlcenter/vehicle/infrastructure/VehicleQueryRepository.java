package org.controlcenter.vehicle.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.vehicle.infrastructure.mybatis.MyBatisVehicleInfoMapper;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleQueryRepository {
	private final MyBatisVehicleInfoMapper myBatisVehicleInfoMapper;

	public VehicleInfoResponse getVehicleInfo(VehicleInfoSearchRequest request) {
		return myBatisVehicleInfoMapper.selectVehicleInfo(request.vehicleNumber());
	}

	public List<RouteResponse> getVehicleRouteFrom(
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

	public List<RouteResponse> getVehicleRouteFromWithPagination(
		Long vehicleId,
		LocalDateTime startTime,
		LocalDateTime endTime,
		Integer interval,
		Integer page,
		Integer size
	) {
		int offset = (page - 1) * size;
		return myBatisVehicleInfoMapper.getVehicleRouteFromWithPagination(
			vehicleId,
			startTime,
			endTime,
			interval,
			size,
			offset
		);
	}

	public VehicleModalResponse.RecentVehicleInfo getRecentVehicleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentVehicleInfo(vehicleId);
	}

	public VehicleModalResponse.RecentCycleInfo getRecentCycleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentCycleInfo(vehicleId);
	}

	public VehicleModalResponse.TodayDrivingHistory getTodayDrivingHistory(Long vehicleId) {
		return myBatisVehicleInfoMapper.getTodayDrivingHistory(vehicleId);
	}

	public String getVehicleStatus(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentVehicleStatus(vehicleId);
	}

	public VehicleEngineStatusResponse getVehicleEngineStatus(Long companyId) {
		return myBatisVehicleInfoMapper.getVehicleEngineStatus(companyId);
	}

	public VehicleModalResponse.VehicleCompanyInfo getVehicleCompanyInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getVehicleCompanyInfo(vehicleId);
	}
}
