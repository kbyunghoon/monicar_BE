package org.emulator.device;

import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;

public final class VehicleConstant {
	public static final long VEHICLE_PRIMARY_KEY = 1234567890L;
	public static final String VEHICLE_NUMBER = "123나1234";
	public static final String TERMINAL_ID = "TID001";
	public static final long MANUFACTURER_ID = 1L;
	public static final int PACKET_VERSION = 1;
	public static final long DEVICE_ID = 1L;

	public static final int BATTERY = 100;

	public static final long MIL = 1_000_000;

	private VehicleConstant() {
		throw new BusinessException(ErrorCode.ILLEGAL_UTILITY_CLASS_ACCESS);
	}
}
