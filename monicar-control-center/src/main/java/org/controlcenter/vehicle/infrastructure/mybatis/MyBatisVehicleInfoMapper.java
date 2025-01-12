package org.controlcenter.vehicle.infrastructure.mybatis;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;

@Mapper
public interface MyBatisVehicleInfoMapper {

	@Select("""
			select
				vi.vehicle_id as vehicleId,
					vi.vehicle_number as vehicleNumber,
				vi.created_at as firstDataAt,
				(
					select ci.created_at
					from cycle_info ci
					where ci.vehicle_id = vi.vehicle_id
					order by ci.interval_at DESC
					limit 1
				) as lastDataAt
			from vehicle_information vi
			where vi.vehicle_number = #{vehicleNumber}
			  and vi.deleted_at is null;
		""")
	VehicleInfoResponse selectVehicleInfo(@Param("vehicleNumber") String vehicleNumber);

	/**
	 * 1억개 데이터 기준
	 * 멀티 컬럼 인덱스 : (vehicle_id, interval_at) 적용
	 */
	@Select("""
		select
		    filtering_cycle_info.lat,
		    filtering_cycle_info.lng,
		    filtering_cycle_info.spd,
		    filtering_cycle_info.interval_at
				from (
		           select
		               *,
		               row_number() over (order by interval_at) as row_num
		           from cycle_info
		           where vehicle_id = #{vehicleId}
		             and #{startTime} <= interval_at
		             and interval_at <= #{endTime}
		       ) as filtering_cycle_info
		  where (row_num - 1) % #{interval} = 0;
		    """)
	List<RouteResponse> getVehicleRouteFrom(
		@Param("vehicleId") Long vehicleId,
		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime,
		@Param("interval") Integer interval
	);
}
