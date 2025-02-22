package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.controlcenter.vehicle.domain.VehicleRegister;

public record VehicleRegisterRequest(
	@NotBlank
	String vehicleNumber,

	@NotNull
	long vehicleTypeId,

	@NotNull
	LocalDate deliveryDate,

	@NotNull
	int drivingDistance
) {
	public VehicleRegister toDomain() {
		return VehicleRegister.of(
			vehicleNumber,
			vehicleTypeId,
			deliveryDate,
			drivingDistance
		);
	}
}
