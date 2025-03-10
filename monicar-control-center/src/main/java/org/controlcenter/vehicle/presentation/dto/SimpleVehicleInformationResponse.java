package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.elasticsearch.document.VehicleInformationDocument;

@Builder
public record SimpleVehicleInformationResponse(
	long vehicleId,
	String vehicleNumber
) {
	public static SimpleVehicleInformationResponse from(VehicleInformationDocument doc) {
		return SimpleVehicleInformationResponse.builder()
			.vehicleId(doc.getId())
			.vehicleNumber(doc.getVehicleNumber())
			.build();
	}
}