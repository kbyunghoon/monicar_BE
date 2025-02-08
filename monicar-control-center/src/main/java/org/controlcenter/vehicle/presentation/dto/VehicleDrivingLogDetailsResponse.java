package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDate;
import java.util.List;

import org.controlcenter.vehicle.domain.BusinessInfo;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.SpecificVehicleInformation;

import lombok.Builder;

@Builder
public record VehicleDrivingLogDetailsResponse(
	LocalDate taxStartPeriod,
	LocalDate taxEndPeriod,
	BusinessInfo businessInfo,
	int taxPeriodDistance,
	int taxPeriodBusinessDistance,
	int businessUseRatio,
	SpecificVehicleInformation vehicleInfo,
	List<DrivingLogDetailsContent> records
) {
}
