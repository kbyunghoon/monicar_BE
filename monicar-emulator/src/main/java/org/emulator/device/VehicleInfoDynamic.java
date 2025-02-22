package org.emulator.device;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record VehicleInfoDynamic(
	@Value("${fixed.vehicle-mdn}")
	long vehicleMdn,
	@Value("${fixed.vehicle-number}")
	String vehicleNumber
) {}
