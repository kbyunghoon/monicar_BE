package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

import org.emulator.device.VehicleInfoConstant;

@Getter
public class Geo {
	private static final long LATITUDE_MIN = -80000000L;
	private static final long LATITUDE_MAX = 80000000L;
	private static final long LONGITUDE_MIN = -180000000L;
	private static final long LONGITUDE_MAX = 180000000L;

	private final long latitude;
	private final long longitude;

	public Geo() {
		this.latitude = 0L;
		this.longitude = 0L;
	}

	public Geo(double latitude, double longitude) {
		this.latitude = Math.clamp((long)(latitude * VehicleInfoConstant.MIL), LATITUDE_MIN, LATITUDE_MAX);
		this.longitude = Math.clamp((long)(longitude * VehicleInfoConstant.MIL), LONGITUDE_MIN, LONGITUDE_MAX);
	}

	@Override
	public String toString() {
		return "latitude= " + (double)latitude / VehicleInfoConstant.MIL +
			", longitude=" + (double)longitude / VehicleInfoConstant.MIL;
	}
}
