package org.emulator.device.infrastructure.external.command;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;

import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OffInfo;
import org.emulator.device.infrastructure.external.command.vo.FixedVehicleInfo;

/**
 * 차량의 시동 ON / OFF 상태에서 전달하는 데이터를 담는 레코드
 *
 * @param mdn     차량 번호 - 차량을 식별하는 고유 번호
 * @param tid     터미널 아이디 - 터미널 장치의 고유 식별자
 * @param mid     제조사 아이디 - 제조사를 구분하는 식별자
 * @param pv      패킷 버전 - 데이터 패킷의 버전 정보
 * @param did     디바이스 아이디 - 설치된 디바이스의 고유 식별자
 * @param onTime  null
 * @param offTime 시동 OFF 시간
 * @param gcd     GPS 상태 - GPS 수신 상태 ('A': 정상, 'V': 비정상, '0': 미장착, 시동 on시 비정상: 'P')
 * @param lat     GPS 위도 - 차량의 현재 위도 좌표(x1000000)
 * @param lng     GPS 경도 - 차량의 현재 경도 좌표(x1000000)
 * @param ang     0
 * @param spd     0
 * @param sum     최근 on부터의 주행 거리
 */
@Builder
public record OffCommand(
	long mdn,
	String tid,
	long mid,
	int pv,
	long did,
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime onTime,
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime offTime,
	GpsStatus gcd,
	long lat,
	long lng,
	int ang,
	int spd,
	int sum
) {
	public static OffCommand from(OffInfo offInfo) {
		FixedVehicleInfo fixedInfo = FixedVehicleInfo.getInstance();
		return OffCommand.builder()
			.mdn(fixedInfo.getMdn())
			.tid(fixedInfo.getTid())
			.mid(fixedInfo.getMid())
			.pv(fixedInfo.getPv())
			.did(fixedInfo.getDid())
			.onTime(offInfo.getOnTime())
			.offTime(offInfo.getOffTime())
			.gcd(offInfo.getGpsStatus())
			.lat(offInfo.getGeo().getLatitude())
			.lng(offInfo.getGeo().getLongitude())
			.ang(offInfo.getDirection().getValue())
			.spd(offInfo.getSpeed().getValue())
			.sum(offInfo.getFromOnToOffDistance().getValue())
			.build();
	}
}