package org.controlcenter.vehicle.infrastructure.jpa;

import static org.controlcenter.company.infrastructure.jpa.entity.QCompanyEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QDepartmentEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QManagerEntity.*;
import static org.controlcenter.history.infrastructure.jpa.entity.QDrivingHistoryEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleInformationEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleTypeEntity.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.cycleinfo.infrastructure.jpa.entity.QCycleInfoEntity;
import org.controlcenter.history.infrastructure.jpa.entity.QDrivingHistoryEntity;
import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.DailyDrivingSummary;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.HourlyDrivingLogs;
import org.controlcenter.vehicle.domain.QBusinessDrivingDetails;
import org.controlcenter.vehicle.domain.QDailyDrivingSummary;
import org.controlcenter.vehicle.domain.QDrivingInfo;
import org.controlcenter.vehicle.domain.QDrivingLog;
import org.controlcenter.vehicle.domain.QDrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.QDrivingUserInfo;
import org.controlcenter.vehicle.domain.QHourlyDrivingLogs;
import org.controlcenter.vehicle.domain.QVehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DrivingLogJpaRepository implements DrivingLogRepository {
	private final JPAQueryFactory queryFactory;

	public List<HourlyDrivingLogs> getHourlyDrivingLogs(Long vehicleId, LocalDate targetDate) {
		QDrivingHistoryEntity drivingHistory = QDrivingHistoryEntity.drivingHistoryEntity;
		QCycleInfoEntity cycleInfo = QCycleInfoEntity.cycleInfoEntity;
		QCycleInfoEntity subCycleInfo = new QCycleInfoEntity("subCycleInfo");

		JPQLQuery<LocalDateTime> intervalAtSubQuery = JPAExpressions
			.select(subCycleInfo.intervalAt.max())
			.from(subCycleInfo)
			.where(
				subCycleInfo.vehicleId.eq(drivingHistory.vehicleId),
				subCycleInfo.intervalAt.loe(drivingHistory.endTime)
			);

		return queryFactory
			.select(
				new QHourlyDrivingLogs(
					drivingHistory.startTime,
					drivingHistory.endTime,
					drivingHistory.drivingDistance,
					cycleInfo.lat.doubleValue(),
					cycleInfo.lng.doubleValue()
				)
			)
			.from(drivingHistory)
			.leftJoin(cycleInfo)
			.on(
				cycleInfo.vehicleId.eq(drivingHistory.vehicleId),
				cycleInfo.intervalAt.eq(intervalAtSubQuery)
			)
			.where(
				drivingHistory.vehicleId.eq(vehicleId),
				drivingHistory.startTime.between(
					targetDate.atStartOfDay(),
					targetDate.atTime(23, 59, 59)
				)
			)
			.orderBy(drivingHistory.startTime.asc())
			.fetch();
	}

	@Override
	public List<DailyDrivingSummary> getDailySummaries(Long vehicleId, LocalDateTime start, LocalDateTime end) {
		QDrivingHistoryEntity q = QDrivingHistoryEntity.drivingHistoryEntity;

		DateTemplate<Date> dateOnly = Expressions.dateTemplate(
			Date.class,
			"DATE({0})",
			q.startTime
		);

		NumberExpression<Long> drivingSeconds = Expressions.numberTemplate(
			Long.class,
			"TIMESTAMPDIFF(SECOND, {0}, {1})",
			q.startTime,
			q.endTime
		);

		return queryFactory
			.select(
				new QDailyDrivingSummary(
					dateOnly,
					q.drivingDistance.sum(),
					drivingSeconds.sum())
			)
			.from(q)
			.where(
				q.vehicleId.eq(vehicleId),
				q.deletedAt.isNull(),
				q.startTime.between(start, end)
			)
			.groupBy(dateOnly)
			.orderBy(dateOnly.asc())
			.fetch();
	}

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
	public Page<DrivingLog> findByVehicleNumber(String vehicleNumber, VehicleSortType sortType, Pageable pageable) {
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
			.orderBy(createOrderSpecifier(sortType))
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
			drivingHistoryEntity.id,
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

	private OrderSpecifier<?>[] createOrderSpecifier(VehicleSortType sortType) {
		if (sortType == null) {
			return new OrderSpecifier[0];
		}

		return switch (sortType) {
			case CREATED_AT_DESC -> new OrderSpecifier[] {
				new OrderSpecifier<>(Order.DESC, vehicleInformationEntity.createdAt)
			};
			case CREATED_AT_ASC -> new OrderSpecifier[] {
				new OrderSpecifier<>(Order.ASC, vehicleInformationEntity.createdAt)
			};
			case VEHICLE_NUMBER_DESC -> new OrderSpecifier[] {
				new OrderSpecifier<>(Order.DESC, vehicleInformationEntity.vehicleNumber)
			};
			case VEHICLE_NUMBER_ASC -> new OrderSpecifier[] {
				new OrderSpecifier<>(Order.ASC, vehicleInformationEntity.vehicleNumber)
			};
			default -> new OrderSpecifier[0];
		};
	}
}
