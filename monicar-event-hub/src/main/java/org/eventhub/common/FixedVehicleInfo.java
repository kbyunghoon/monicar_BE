<<<<<<<< HEAD:monicar-event-hub/src/main/java/org/eventhub/dto/FixedVehicleInfo.java
package org.eventhub.dto;
========
package org.producer.common;
>>>>>>>> b6ebbcf (feature(producer): 이벤트 받는 서버로 코드 이전):monicar-event-hub/src/main/java/org/eventhub/common/FixedVehicleInfo.java

import org.eventhub.common.constant.VehicleConstant;

import lombok.Getter;

@Getter
public class FixedVehicleInfo {
	private static final FixedVehicleInfo INSTANCE = new FixedVehicleInfo();

	private final long mdn;
	private final String num;
	private final String tid;
	private final long mid;
	private final int pv;
	private final long did;

	private FixedVehicleInfo() {
		this.mdn = VehicleConstant.VEHICLE_PRIMARY_KEY;
		this.num = VehicleConstant.VEHICLE_NUMBER;
		this.tid = VehicleConstant.TERMINAL_ID;
		this.mid = VehicleConstant.MANUFACTURER_ID;
		this.pv = VehicleConstant.PACKET_VERSION;
		this.did = VehicleConstant.DEVICE_ID;
	}

	public static FixedVehicleInfo getInstance() {
		return INSTANCE;
	}
}
