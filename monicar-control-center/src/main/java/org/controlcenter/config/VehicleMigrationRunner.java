package org.controlcenter.config;

import lombok.RequiredArgsConstructor;

import org.controlcenter.vehicle.application.VehicleInformationMigrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleMigrationRunner implements CommandLineRunner {
	private final VehicleInformationMigrationService migrationService;

	@Override
	public void run(String... args) throws Exception {
		migrationService.migrateAll();
	}
}
