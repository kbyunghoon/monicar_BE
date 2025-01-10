package org.controlcenter.vehicle.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.controlcenter.cycleinfo.domain.GpsStatus;
import org.controlcenter.vehicle.domain.VehicleEventCreate;
import org.controlcenter.vehicle.domain.VehicleEventType;

import java.math.BigDecimal;

public record KeyOnRequest(
		@NotNull(message = "차량 번호는 비어 있을 수 없습니다.")
		Long mdn,
		@NotBlank(message = "터미널 아이디는 null 또는 비어 있을 수 없습니다.")
		String tid,
		@NotNull(message = "제조사 아이디는 비어 있을 수 없습니다.")
		Integer mid,
		@NotNull(message = "패킷 버전은 비어 있을 수 없습니다.")
		Integer pv,
		@NotNull(message = "디바이스 아이디는 비어 있을 수 없습니다.")
		Integer did,
		@NotBlank(message = "차량 시동 On 시간은 비어 있을 수 없습니다.")
		String onTime,
		@NotBlank(message = "차량 시동 Off 시간은 비어 있을 수 없습니다.")
		String offTime,
		@NotNull(message = "GPS 상태는 null 또는 비어 있을 수 없습니다.")
		GpsStatus gcd,
		@NotNull(message = "GPS 위도는 비어 있을 수 없습니다.")
		BigDecimal lat,
		@NotNull(message = "GPS 경도는 비어 있을 수 없습니다.")
		BigDecimal lon,
		@NotNull(message = "방향은 비어 있을 수 없습니다.")
		Integer ang,
		@NotNull(message = "속도는 비어 있을 수 없습니다.")
		Integer spd,
		@NotNull(message = "누적 주행 거리는 비어 있을 수 없습니다.")
		Integer sum
) {
	public VehicleEventCreate toDomain(final Long vehicleId) {
		return VehicleEventCreate.of(
				vehicleId,
				VehicleEventType.ON,
				onTime
		);
	}
}
