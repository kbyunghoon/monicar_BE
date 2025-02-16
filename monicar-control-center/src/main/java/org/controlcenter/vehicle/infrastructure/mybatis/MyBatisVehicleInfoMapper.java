package org.controlcenter.vehicle.infrastructure.mybatis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.controlcenter.vehicle.infrastructure.mybatis.dto.GeoCoordinateDetailsDto;
import org.controlcenter.vehicle.infrastructure.mybatis.dto.GeoCoordinateDto;
import org.controlcenter.vehicle.presentation.RouteResponseWithStatus;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.RouteResponseWithAng;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;

@Mapper
public interface MyBatisVehicleInfoMapper {

	@Select("""
			select
				vi.vehicle_id as vehicleId,
					vi.vehicle_number as vehicleNumber,
				(
					select ci.interval_at
					from cycle_info ci
					where ci.vehicle_id = vi.vehicle_id
					order by ci.interval_at ASC
					limit 1
				) as firstDateAt,
				(
					select ci.interval_at
					from cycle_info ci
					where ci.vehicle_id = vi.vehicle_id
					order by ci.interval_at DESC
					limit 1
				) as lastDateAt
			from vehicle_information vi
			where vi.vehicle_number = #{vehicleNumber}
			  and vi.deleted_at is null;
		""")
	Optional<VehicleInfoResponse> selectVehicleInfo(@Param("vehicleNumber") String vehicleNumber);

	/**
	 * 1억개 데이터 기준
	 * 멀티 컬럼 인덱스 : (vehicle_id, interval_at) 적용
	 */
	@Select("""
		select
		    filtering_cycle_info.lat,
		    filtering_cycle_info.lng,
		    filtering_cycle_info.ang,
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
	List<RouteResponseWithAng> getVehicleRouteFrom(
		@Param("vehicleId") Long vehicleId,

		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime,
		@Param("interval") Integer interval
	);

	/**
	 * 1억개 데이터 기준
	 * 멀티 컬럼 인덱스 : (vehicle_id, interval_at) 적용
	 * 페이지 네이션 추가
	 */
	@Select("""
		SELECT
			filtering_cycle_info.lat,
			filtering_cycle_info.lng,
			filtering_cycle_info.spd,
			filtering_cycle_info.interval_at
		FROM (
			SELECT
				*,
				ROW_NUMBER() OVER (ORDER BY interval_at) AS row_num
			FROM cycle_info
			WHERE vehicle_id = #{vehicleId}
			  AND #{startTime} <= interval_at
			  AND interval_at <= #{endTime}
		) AS filtering_cycle_info
		WHERE (row_num - 1) % #{interval} = 0
		LIMIT #{size} OFFSET #{offset};
	""")
	List<RouteResponse> getVehicleRouteFromWithPagination(
		@Param("vehicleId") Long vehicleId,
		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime,
		@Param("interval") Integer interval,
		@Param("size") Integer size,
		@Param("offset") Integer offset
	);

	/**
	 * 1억개 데이터 기준
	 * 멀티 컬럼 인덱스 : (vehicle_id, interval_at) 적용
	 * 전체 데이터 개수 조회
	 */
	@Select("""
		SELECT COUNT(*) 
		FROM (
			SELECT ROW_NUMBER() OVER (ORDER BY interval_at) AS row_num
			FROM cycle_info
			WHERE vehicle_id = #{vehicleId}
			  AND #{startTime} <= interval_at
			  AND interval_at <= #{endTime}
		) AS filtered_data
		WHERE (row_num - 1) % #{interval} = 0
	""")
	int countVehicleRoutes(
		@Param("vehicleId") Long vehicleId,
		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime,
		@Param("interval") Integer interval
	);

	@Select("""
		SELECT
		  ci.lat,
		  ci.lng,
		  ci.spd,
		  vi.status,
		  ci.interval_at
  		FROM cycle_info ci
           JOIN vehicle_information vi ON ci.vehicle_id = vi.vehicle_id
  		WHERE ci.vehicle_id = #{vehicleId}
    		AND vi.status = 'IN_OPERATION'
    		AND ci.interval_at <= DATE_SUB(#{currentTime}, INTERVAL 2 MINUTE)
      	ORDER BY ci.interval_at DESC
       	LIMIT 65;
		""")
	List<RouteResponseWithStatus> getRecentRoutesByVehicle(
		@Param("vehicleId") Long vehicleId,
		@Param("currentTime") LocalDateTime currentTime
	);

	@Select("""
			select
				vi.vehicle_id,
				vi.vehicle_number,
				vi.status,
				max(case when ve.type = 'on' then ve.event_at end) as last_on_time,
				max(case when ve.type = 'off' then ve.event_at end) as last_off_time
			from
				vehicle_information vi
			join
				vehicle_event ve on ve.vehicle_id = vi.vehicle_id
			where
				vi.vehicle_id = #{vehicleId};
		""")
	Optional<VehicleModalResponse.RecentVehicleInfo> getRecentVehicleInfo(@Param("vehicleId") Long vehicleId);

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
	Optional<VehicleModalResponse.RecentCycleInfo> getRecentCycleInfo(@Param("vehicleId") Long vehicleId);

	@Select("""
		select
			IFNULL(sum(di.driving_distance), 0),
			IFNULL(sum(timestampdiff(second, di.start_time, di.end_time)), 0)
		from
			driving_history di
		where
			di.vehicle_id = #{vehicleId}
			and date(end_time) = curdate();
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

	@Select("""
		select
		  vi.vehicle_id,
				  vi.vehicle_number,
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
	List<GeoCoordinateDetailsDto> findCoordinateDetailsByCompanyId(
		@Param("companyId") Long companyId,
		@Param("neLat") int neLat,
		@Param("neLng") int neLng,
		@Param("swLat") int swLat,
		@Param("swLng") int swLng
	);

	@Select("""
		select
			c.company_name
		from vehicle_information vi
		join company c
			on c.company_id = vi.company_id
		where vi.vehicle_id = #{vehicleId};
		""")
	VehicleModalResponse.VehicleCompanyInfo getVehicleCompanyInfo(Long vehicleId);
}
