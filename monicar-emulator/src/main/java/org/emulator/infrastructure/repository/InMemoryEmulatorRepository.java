package org.emulator.infrastructure.repository;

import org.emulator.application.port.EmulatorRepository;
import org.emulator.domain.VehicleInfo;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEmulatorRepository implements EmulatorRepository {
    private VehicleInfo vehicleInfo = new VehicleInfo();

	@Override
	public int getTotalDistance() {
		return vehicleInfo.getTotalDistance();
	}

	@Override
	public String getGpsStatus() {
		return vehicleInfo.getGpsStatus();
	}
}
