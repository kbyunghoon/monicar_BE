package org.emulator.device;

import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;

public class VehicleInfoConstant {
	public static final String TERMINAL_ID = "TID001";
	public static final long MANUFACTURER_ID = 1L;
	public static final int PACKET_VERSION = 1;
	public static final long DEVICE_ID = 1L;

	public static final int BATTERY = 100;

	public static final long MIL = 1_000_000;

	private VehicleInfoConstant() {
		throw new BusinessException(ErrorCode.ILLEGAL_UTILITY_CLASS_ACCESS);
	}
}
