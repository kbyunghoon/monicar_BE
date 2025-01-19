package org.controlcenter.vehicle.application.port;

import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;
import org.controlcenter.vehicle.domain.VehicleInformation;

public interface VehicleRepository {
	Optional<VehicleInformation> findById(Long vehicleId);
	Optional<VehicleInformation> findByMdn(Long mdn);
	List<GeoCoordinate> findCoordinatesByCompanyId(ClusterCreateCommand command, Long companyId);
	Optional<VehicleInformation> findByVehicleNumber(String vehicleNumber);
}
