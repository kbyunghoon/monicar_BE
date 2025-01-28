package org.controlcenter.vehicle.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.BusinessInfo;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.DrivingLogSummary;
import org.controlcenter.vehicle.domain.SpecificVehicleInformation;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.controlcenter.vehicle.infrastructure.VehicleInformationRepositoryAdapter;
import org.controlcenter.vehicle.presentation.dto.VehicleDrivingLogDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DrivingLogService {
	private final DrivingLogRepository drivingLogRepository;
	private final VehicleInformationRepositoryAdapter vehicleInformationRepository;

	@Transactional(readOnly = true)
	public Page<DrivingLog> getDrivingLogList(String vehicleNumber, VehicleSortType vehicleSortType,
		Pageable pageable) {
		return drivingLogRepository.findByVehicleNumber(vehicleNumber, vehicleSortType, pageable);
	}

	@Transactional(readOnly = true)
	public VehicleDrivingLogDetailsResponse getVehicleDrivingLogDetails(
		Long vehicleId,
		LocalDate startDate,
		LocalDate endDate) {
		if (startDate == null) {
			startDate = LocalDate.now().minusYears(1);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}

		validateVehicleId(vehicleId);

		VehicleHeaderInfo header = drivingLogRepository.findVehicleHeaderInfoByVehicleId(vehicleId);

		List<DrivingLogDetailsContent> drivingLogsPage = drivingLogRepository.findDrivingLogsByVehicleIdAndDateRange(
			vehicleId, startDate, endDate.plusDays(1));

		Integer sumDistance = Optional.ofNullable(
			drivingLogRepository.sumByVehicleIdAndDateRange(vehicleId, startDate, endDate.plusDays(1))
		).orElse(0);

		SpecificVehicleInformation vehicleType = SpecificVehicleInformation.builder()
			.vehicleNumber(header.getVehicleNumber())
			.vehicleModel(header.getVehicleTypesName())
			.build();

		BusinessInfo businessInfo = BusinessInfo.builder()
			.businessName(header.getCompanyName())
			.businessRegistrationNumber(header.getBusinessRegistrationNumber())
			.build();

		DrivingLogSummary summary = drivingLogsPage.stream()
			.collect(DrivingLogSummary::new,
				DrivingLogSummary::accumulate,
				DrivingLogSummary::combine);

		return VehicleDrivingLogDetailsResponse.builder()
			.taxStartPeriod(startDate)
			.taxEndPeriod(endDate)
			.businessInfo(businessInfo)
			.vehicleType(vehicleType)
			.records(drivingLogsPage)
			.taxPeriodDistance(sumDistance)
			.taxPeriodBusinessDistance(summary.getCommuteCount())
			.businessUseRatio(summary.getCommutePercent())
			.build();
	}

	private void validateVehicleId(Long vehicleId) {
		vehicleInformationRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_FOUND));
	}
}