package org.emulator.infrastructure.external.command;

import java.time.format.DateTimeFormatter;

import org.emulator.common.VehicleConstant;
import org.emulator.domain.OnInfo;

/**
 * 차량의 시동 ON / OFF 상태에서 전달하는 데이터를 담는 Dto
 *
 * @param mdn 차량 번호 - 차량을 식별하는 고유 번호
 * @param tid 터미널 아이디 - 터미널 장치의 고유 식별자
 * @param mid 제조사 아이디 - 제조사를 구분하는 식별자
 * @param pv 패킷 버전 - 데이터 패킷의 버전 정보
 * @param did 디바이스 아이디 - 설치된 디바이스의 고유 식별자
 * @param onTime 시동 ON 시간
 * @param offTime 직전 시동 OFF 시간
 * @param gcd GPS 상태 - GPS 수신 상태 ('A': 정상, 'V': 비정상, '0': 미장착, 시동 on시 비정상: 'P')
 * @param lat GPS 위도 - 차량의 현재 위도 좌표
 * @param lon GPS 경도 - 차량의 현재 경도 좌표
 * @param ang 방향 - 차량의 현재 진행 방향
 * @param spd 속도 - 차량의 현재 속도
 * @param sum 누적 주행 거리 - 차량의 현재 총 주행 거리
 */
public record OnCommand(
	String mdn,
	String tid,
	String mid,
	String pv,
	String did,
	String onTime,
	String offTime,
	String gcd,
	String lat,
	String lon,
	String ang,
	String spd,
	String sum
) {
	public static OnCommand of(OnInfo onInfo) {
		DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		String onTime = (onInfo.getOnTime() != null)
			? onInfo.getOnTime().format(DATE_TIME_FORMATTER)
			: "";

		String offTime = (onInfo.getOffTime() != null)
			? onInfo.getOffTime().format(DATE_TIME_FORMATTER)
			: "";

		String lat = String.format("%.6f", onInfo.getLatitude());
		String lon = String.format("%.6f", onInfo.getLongitude());
		String ang = String.valueOf(Math.min(Math.max(onInfo.getDirection(), 0), 365));
		String spd = String.valueOf(Math.min(Math.max(onInfo.getSpeed(), 0), 255));
		String sum = String.valueOf(Math.min(Math.max(onInfo.getTotalDistance(), 0), 9999999));

		return new OnCommand(
			VehicleConstant.VEHICLE_NUM,
			VehicleConstant.TERMINAL_ID,
			VehicleConstant.MANUFACTURER_ID,
			VehicleConstant.PACKET_VERSION,
			VehicleConstant.DEVICE_ID,
			onTime,
			offTime,
			onInfo.getGpsStatus(),
			lat,
			lon,
			ang,
			spd,
			sum
		);
	}
}
