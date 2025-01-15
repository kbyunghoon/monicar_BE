package org.controlcenter.vehicle.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.vehicle.infrastructure.mybatis.MyBatisVehicleInfoMapper;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
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

	public VehicleModalResponse.RecentVehicleInfo getRecentVehicleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentVehicleInfo(vehicleId);
	}

	public VehicleModalResponse.RecentCycleInfo getRecentCycleInfo(Long vehicleId) {
		return myBatisVehicleInfoMapper.getRecentCycleInfo(vehicleId);
	}

	public VehicleModalResponse.TodayDrivingHistory getTodayDrivingHistory(Long vehicleId) {
		return myBatisVehicleInfoMapper.getTodayDrivingHistory(vehicleId);
	}
}
