package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

import org.controlcenter.vehicle.domain.BusinessInfo;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.SpecificVehicleInformation;

@Builder
public record VehicleDrivingLogDetailsResponse(
	LocalDate taxStartPeriod,
	LocalDate taxEndPeriod,
	BusinessInfo businessInfo,
	SpecificVehicleInformation vehicleType,
	List<DrivingLogDetailsContent> records
) {
}
