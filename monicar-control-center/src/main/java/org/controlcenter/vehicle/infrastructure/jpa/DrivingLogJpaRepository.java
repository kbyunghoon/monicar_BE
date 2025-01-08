package org.controlcenter.vehicle.infrastructure.jpa;

import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleInformationEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleTypeEntity.*;

import java.util.List;

import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.QDrivingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DrivingLogJpaRepository implements DrivingLogRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<DrivingLog> findByVehicleNumber(String vehicleNumber, Pageable pageable) {
		List<DrivingLog> content = queryFactory
			.select(new QDrivingLog(
				vehicleInformationEntity.id,
				vehicleInformationEntity.vehicleNumber,
				vehicleTypeEntity.vehicleTypesName,
				vehicleInformationEntity.status
			))
			.from(vehicleInformationEntity)
			.join(vehicleTypeEntity)
			.on(vehicleInformationEntity.vehicleTypeId.eq(vehicleTypeEntity.id))
			.where(
				vehicleInformationEntity.vehicleNumber.contains(vehicleNumber)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(Wildcard.count)
			.from(vehicleInformationEntity)
			.where(vehicleInformationEntity.vehicleNumber.contains(vehicleNumber));

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}
}
