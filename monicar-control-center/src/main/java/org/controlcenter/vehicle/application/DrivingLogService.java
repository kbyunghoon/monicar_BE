package org.controlcenter.vehicle.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.BusinessInfo;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.SpecificVehicleInformation;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
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

	@Transactional(readOnly = true)
	public Page<DrivingLog> getDrivingLogList(String vehicleNumber, Pageable pageable) {
		return drivingLogRepository.findByVehicleNumber(vehicleNumber, pageable);
	}

	@Transactional(readOnly = true)
	public VehicleDrivingLogDetailsResponse getVehicleDrivingLogDetails(
		Long vehicleId,
		LocalDate startDate,
		LocalDate endDate) {

		Optional<VehicleHeaderInfo> maybeHeader = drivingLogRepository.findVehicleHeaderInfoByVehicleId(vehicleId);

		if (maybeHeader.isEmpty()) {
			return null;
		}

		VehicleHeaderInfo header = maybeHeader.get();

		List<DrivingLogDetailsContent> drivingLogsPage = drivingLogRepository.findDrivingLogsByVehicleIdAndDateRange(
			vehicleId, startDate, endDate.plusDays(1));

		SpecificVehicleInformation vehicleType = SpecificVehicleInformation.builder()
			.vehicleNumber(header.getVehicleNumber())
			.vehicleTypesName(header.getVehicleTypesName())
			.build();

		BusinessInfo businessInfo = BusinessInfo.builder()
			.businessId(header.getCompanyId())
			.businessName(header.getCompanyName())
			.businessRegistrationNumber(header.getBusinessRegistrationNumber())
			.build();

		return VehicleDrivingLogDetailsResponse.builder()
			.taxStartPeriod(startDate)
			.taxEndPeriod(endDate)
			.businessInfo(businessInfo)
			.vehicleType(vehicleType)
			.records(drivingLogsPage)
			.build();
	}
}