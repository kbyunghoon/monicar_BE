package org.eventhub.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.eventhub.domain.GpsStatus;
import org.eventhub.domain.VehicleOnEventCreate;
import org.eventhub.domain.VehicleEventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
		@JsonFormat(pattern = "yyyyMMddHHmmss")
		@NotNull(message = "차량 시동 On 시간은 비어 있을 수 없습니다.")
		String onTime,
		String offTime,
		@NotNull(message = "GPS 상태는 null 또는 비어 있을 수 없습니다.")
		GpsStatus gcd,
		@NotNull(message = "GPS 위도는 비어 있을 수 없습니다.")
		Long lat,
		@NotNull(message = "GPS 경도는 비어 있을 수 없습니다.")
		Long lng,
		@NotNull(message = "방향은 비어 있을 수 없습니다.")
		Integer ang,
		@NotNull(message = "속도는 비어 있을 수 없습니다.")
		Integer spd,
		@NotNull(message = "누적 주행 거리는 비어 있을 수 없습니다.")
		Long sum
) {
	public VehicleOnEventCreate toDomain(final Long existedVehicleId, final long existedSum) {
		return VehicleOnEventCreate.of(
				existedVehicleId,
				existedSum,
				VehicleEventType.ON,
				onTime,
				sum
		);
	}
}
