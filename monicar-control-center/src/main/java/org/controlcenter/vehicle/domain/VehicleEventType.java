package org.controlcenter.vehicle.domain;

public enum VehicleEventType {
	ON,
	OFF;

	public boolean isOn() {
		return this == ON;
	}

	public boolean isOff() {
		return this == OFF;
	}
}
