package org.controlcenter.vehicle.presentation.dto;

public record CommonResponse(
	String rstCd,
	String rstMsg,
	String mdn
) {
}