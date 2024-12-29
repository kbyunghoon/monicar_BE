package org.emulator.domain;

import org.emulator.common.VehicleConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field vehicleStartTime 시동 ON 데이터 이력 목록
 * @field vehicleStopTime  시동 OFF 시간 이력 목록
 */
public class VehicleInfo {
	private String vehicleNumber;
	private final String terminalId = VehicleConstant.TERMINAL_ID;
	private final String manufacturerId = VehicleConstant.MANUFACTURER_ID;
	private final String packetVersion = VehicleConstant.PACKET_VERSION;
	private final String deviceId = VehicleConstant.DEVICE_ID;
	private List<OnInfo> vehicleOnInfoList;
	private List<OffInfo> vehicleOffInfoList;

	private ControlInfo controlInfo;
	private GeoPoint geoPoint;

	private int totalDistance;
	private String gpsStatus;

    public OnInfo addOnInfo(OnInfo data) {
		if (vehicleOnInfoList == null) {
			vehicleOnInfoList = new ArrayList<>();
		}
		vehicleOnInfoList.add(data);

		return data;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}
}
