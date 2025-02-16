package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

import org.emulator.config.ApplicationContextProvider;
import org.emulator.device.VehicleInfoConstant;
import org.emulator.device.VehicleInfoDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FixedVehicleInfo {
	private static FixedVehicleInfo INSTANCE;

	private final long mdn;
	private final String num;
	private final String tid;
	private final long mid;
	private final int pv;
	private final long did;

	@Autowired
	public FixedVehicleInfo(VehicleInfoDynamic vehicleInfoDynamic) {
		this.mdn = vehicleInfoDynamic.vehicleMdn();
		this.num = vehicleInfoDynamic.vehicleNumber();
		this.tid = VehicleInfoConstant.TERMINAL_ID;
		this.mid = VehicleInfoConstant.MANUFACTURER_ID;
		this.pv = VehicleInfoConstant.PACKET_VERSION;
		this.did = VehicleInfoConstant.DEVICE_ID;
		INSTANCE = this;
	}

	public static FixedVehicleInfo getInstance() {
		if (INSTANCE == null) {
			INSTANCE = ApplicationContextProvider.getApplicationContext().getBean(FixedVehicleInfo.class);
		}
		return INSTANCE;
	}
}
