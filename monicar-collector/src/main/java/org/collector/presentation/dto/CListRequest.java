package org.collector.presentation.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CListRequest(
	@NotNull(message = "발생시간은 필수 입력값입니다.")
	Integer sec,

	@NotNull(message = "유효하지 않은 GPS상태 값이 입력되었습니다.")
	GCD gcd,

	@NotNull(message = "GPS 위도는 필수 입력값입니다.")
	@Pattern(regexp = "^[0-9]{7}$", message = "GPS 위도는 7자리 숫자여야 합니다.")
	String lat,

	@NotNull(message = "GPS 경도는 필수 입력값입니다.")
	@Pattern(regexp = "^[0-9]{8}$", message = "GPS 경도는 8자리 숫자여야 합니다.")
	String lon,

	@Range(min = 0, max = 365, message = "방향은 0 ~ 365 사이여야 합니다.")
	@NotNull(message = "방향은 필수 입력값입니다.")
	Integer ang,

	@NotNull(message = "속도는 필수 입력값입니다.")
	@Range(min = 0, max = 255, message = "속도는 0 ~ 255 사이여야 합니다.")
	Integer spd,

	@NotNull(message = "누적 주행거리는 필수 입력값입니다.")
	@Range(min = 0, max = 9999999, message = "누적 주행거리는 0 ~ 9999999 사이여야 합니다.")
	Integer sum,

	@NotNull(message = "배터리 전압은 필수 입력값입니다.")
	@Range(min = 0, max = 9999, message = "배터리 전압은 0 ~ 9999 사이여야 합니다.")
	Integer bat
) {
}
