package org.controlcenter.vehicle.infrastructure.mybatis;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.controlcenter.vehicle.infrastructure.mybatis.dto.GeoCoordinateDto;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;

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

	@Select("""
			select
				vi.vehicle_id,
				vi.vehicle_number,
				(
					select ve2.type
					from vehicle_event ve2
					where ve2.vehicle_id = #{vehicleId}
					order by ve2.event_at desc
					limit 1
				) as status,
				max(case when ve.type = 'on' then ve.event_at end) as last_on_time,
				max(case when ve.type = 'off' then ve.event_at end) as last_off_time
			from
				vehicle_information vi
			join
				vehicle_event ve on ve.vehicle_id = vi.vehicle_id
			where
				vi.vehicle_id = #{vehicleId};
		""")
	VehicleModalResponse.RecentVehicleInfo getRecentVehicleInfo(@Param("vehicleId") Long vehicleId);

	@Select("""
		select
			ci.spd,
			ci.lat,
			ci.lng,
			ci.interval_at
		from cycle_info ci
		where ci.vehicle_id = #{vehicleId}
		order by ci.interval_at DESC
		limit 1
		""")
	VehicleModalResponse.RecentCycleInfo getRecentCycleInfo(@Param("vehicleId") Long vehicleId);

	@Select("""
		select
			sum(driving_distance),
			sum(timestampdiff(second, start_time, end_time))
		from
			driving_history
		where
			date(end_time) = curdate();
		""")
	VehicleModalResponse.TodayDrivingHistory getTodayDrivingHistory(@Param("vehicleId") Long vehicleId);

	@Select("""
		select
		  ci.lat,
		  ci.lng
		from vehicle_information vi
		join cycle_info ci
		  on vi.vehicle_id = ci.vehicle_id
		where vi.company_id = #{companyId}
		  and ci.interval_at = (
			  select max(interval_at)
			  from cycle_info
			  where vehicle_id = vi.vehicle_id
		  )
		  and ci.lat between #{swLat} and #{neLat}
		  and ci.lng between #{swLng} and #{neLng};
		""")
	List<GeoCoordinateDto> findCoordinatesByCompanyId(
		@Param("companyId") Long companyId,
		@Param("neLat") int neLat,
		@Param("neLng") int neLng,
		@Param("swLat") int swLat,
		@Param("swLng") int swLng
	);

	@Select("""
		select ve.type
		from vehicle_event ve
		where ve.vehicle_id = #{vehicleId}
		order by ve.event_at desc
		limit 1;
		""")
	String getRecentVehicleStatus(@Param("vehicleId") Long vehicleId);

	@Select("""
		WITH filtered_events AS (
			SELECT
				ve.vehicle_id,
				ve.type
			FROM vehicle_event ve
			JOIN (
				SELECT
					vehicle_id,
					MAX(event_at) AS latest_event_at
				FROM vehicle_event
				GROUP BY vehicle_id
			) latest
			ON ve.vehicle_id = latest.vehicle_id
			AND ve.event_at = latest.latest_event_at
		)
		SELECT
			COUNT(vi.vehicle_id) AS allVehicles,
			COUNT(CASE WHEN fe.type = 'on' THEN vi.vehicle_id END) AS engineOnVehicles,
			COUNT(CASE WHEN fe.type = 'off' THEN vi.vehicle_id END) AS engineOffVehicles
		FROM
			vehicle_information vi
		JOIN filtered_events fe ON vi.vehicle_id = fe.vehicle_id
		WHERE
			vi.company_id = #{companyId};
		""")
	VehicleEngineStatusResponse getVehicleEngineStatus(Long companyId);
}
