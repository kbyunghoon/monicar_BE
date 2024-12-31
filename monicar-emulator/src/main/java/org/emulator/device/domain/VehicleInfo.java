package org.emulator.device.domain;

import java.util.ArrayList;
import java.util.List;

import org.emulator.device.VehicleConstant;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field vehicleOnInfoList 시동 ON 데이터 이력 목록
 * @field vehicleOffInfoList  시동 OFF 시간 이력 목록
 */
public class VehicleInfo {
	private String vehicleNumber;
	private final String terminalId = VehicleConstant.TERMINAL_ID;
	private final String manufacturerId = VehicleConstant.MANUFACTURER_ID;
	private final String packetVersion = VehicleConstant.PACKET_VERSION;
	private final String deviceId = VehicleConstant.DEVICE_ID;
	private List<OffInfo> vehicleOffInfoList;
	private ControlInfo controlInfo;
	private GeoPoint geoPoint;
	private int totalDistance;

	public int getTotalDistance() {
		return totalDistance;
	}
}
