package org.controlcenter.vehicle.infrastructure.jpa;

import static org.controlcenter.company.infrastructure.jpa.entity.QCompanyEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QDepartmentEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QManagerEntity.*;
import static org.controlcenter.history.infrastructure.jpa.entity.QDrivingHistoryEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleInformationEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleTypeEntity.*;

import java.time.LocalDate;
import java.util.List;

import org.controlcenter.history.domain.UsePurpose;
import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.QBusinessDrivingDetails;
import org.controlcenter.vehicle.domain.QDrivingInfo;
import org.controlcenter.vehicle.domain.QDrivingLog;
import org.controlcenter.vehicle.domain.QDrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.QDrivingUserInfo;
import org.controlcenter.vehicle.domain.QVehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
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
	public Integer sumByVehicleIdAndDateRange(Long vehicleId, LocalDate startDate, LocalDate endDate) {
		return queryFactory
			.select(drivingHistoryEntity.drivingDistance.sum())
			.from(drivingHistoryEntity)
			.where(
				drivingHistoryEntity.vehicleId.eq(vehicleId),
				drivingHistoryEntity.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay())
			)
			.fetchOne();
	}

	@Override
	public Page<DrivingLog> findByVehicleNumber(String vehicleNumber, Pageable pageable) {
		List<DrivingLog> content = queryFactory
			.select(new QDrivingLog(
				vehicleInformationEntity.id,
				vehicleInformationEntity.vehicleNumber,
				vehicleTypeEntity.vehicleTypesName,
				vehicleInformationEntity.drivingDays,
				vehicleInformationEntity.sum,
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

	public VehicleHeaderInfo findVehicleHeaderInfoByVehicleId(Long vehicleId) {
		return queryFactory
			.select(new QVehicleHeaderInfo(
				vehicleInformationEntity.id,
				vehicleInformationEntity.vehicleNumber,
				vehicleTypeEntity.vehicleTypesName,
				companyEntity.id,
				companyEntity.companyName,
				companyEntity.businessRegistrationNumber
			))
			.from(vehicleInformationEntity)
			.join(vehicleTypeEntity).on(vehicleInformationEntity.vehicleTypeId.eq(vehicleTypeEntity.id))
			.join(companyEntity).on(vehicleInformationEntity.companyId.eq(companyEntity.id))
			.where(vehicleInformationEntity.id.eq(vehicleId))
			.fetchOne();
	}

	@Override
	public List<DrivingLogDetailsContent> findDrivingLogsByVehicleIdAndDateRange(
		Long vehicleId, LocalDate startDate, LocalDate endDate) {

		return queryFactory
			.select(createDrivingLogDetailsProjection())
			.from(drivingHistoryEntity)
			.leftJoin(managerEntity).on(drivingHistoryEntity.driverEmail.eq(managerEntity.email))
			.leftJoin(departmentEntity).on(managerEntity.departmentId.eq(departmentEntity.id))
			.where(
				drivingHistoryEntity.vehicleId.eq(vehicleId),
				drivingHistoryEntity.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay())
			)
			.fetch();
	}

	private QDrivingLogDetailsContent createDrivingLogDetailsProjection() {
		return new QDrivingLogDetailsContent(
			drivingHistoryEntity.createdAt.as("usageDate"),
			createDrivingUserInfoProjection(),
			createDrivingInfoProjection()
		);
	}

	private QDrivingUserInfo createDrivingUserInfoProjection() {
		return new QDrivingUserInfo(
			departmentEntity.departmentName,
			managerEntity.nickname
		);
	}

	private QDrivingInfo createDrivingInfoProjection() {
		return new QDrivingInfo(
			drivingHistoryEntity.initialOdometer,
			drivingHistoryEntity.finalOdometer,
			drivingHistoryEntity.drivingDistance,
			createBusinessDrivingDetailsProjection()
		);
	}

	private QBusinessDrivingDetails createBusinessDrivingDetailsProjection() {
		return new QBusinessDrivingDetails(
			drivingHistoryEntity.usePurpose,
			drivingHistoryEntity.drivingDistance
		);
	}
}
