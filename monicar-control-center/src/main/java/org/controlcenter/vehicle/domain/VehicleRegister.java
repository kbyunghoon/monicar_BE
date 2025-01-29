package org.controlcenter.vehicle.domain;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleRegister {
	public static final String REGEX_VEHICLE_NUMBER = "^\\d{2,3}[가-힣]\\d{4}$";

	private String vehicleNumber;
	private long vehicleTypeId;
	private LocalDate deliveryDate;
	private int drivingDistance;

	public VehicleRegister(String vehicleNumber, long vehicleTypeId, LocalDate deliveryDate, int drivingDistance) {
		validateVehicleNumber(vehicleNumber);
		this.vehicleNumber = vehicleNumber;
		this.vehicleTypeId = vehicleTypeId;
		this.deliveryDate = deliveryDate;
		this.drivingDistance = drivingDistance;
	}

	public static VehicleRegister of(String vehicleNumber, long vehicleTypeId, LocalDate deliveryDate,
		int drivingDistance) {
		return new VehicleRegister(vehicleNumber, vehicleTypeId, deliveryDate, drivingDistance);
	}

	private static void validateVehicleNumber(String vehicleNumber) {
		if (!Pattern.matches(REGEX_VEHICLE_NUMBER, vehicleNumber)) {
			throw new BusinessException(ErrorCode.VEHICLE_NUMBER_ERROR);
		}
	}
}
