package org.common.dto;

public record CListRequest(
	String sec,
	GCD gcd,
	String lat,
	String lng,
	String ang,
	String spd,
	String sum,
	String bat
) {
}
