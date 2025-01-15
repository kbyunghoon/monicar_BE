package org.emulator.device.infrastructure.repository;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.infrastructure.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEmulatorRepository implements EmulatorRepository {
	private final VehicleEntity vehicleEntity = new VehicleEntity();

	@Override
	public int startLogDistance() {
		return vehicleEntity.resetCurrentDistance();
	}

	@Override
	public int getCurrentDistance() {
		return vehicleEntity.getTotalDistance();
	}

	@Override
	public int updateCurrentDistance(int intervalDistance) {
		return vehicleEntity.addCurrentDistance(intervalDistance);
	}

	@Override
	public boolean isTurnOn() {
		return vehicleEntity.isOn();
	}

	@Override
	public void turnOn() {
		vehicleEntity.turnOn();
	}

	@Override
	public void turnOff() {
		vehicleEntity.turnOff();
	}
}
