package org.controlcenter.vehicle.application.port;

import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinateDetails;

public interface VehicleRepository {
	Optional<VehicleInformation> findById(Long vehicleId);
	Optional<VehicleInformation> findByMdn(Long mdn);
	Optional<VehicleInformation> findByVehicleNumber(String vehicleNumber);
	List<GeoCoordinate> findCoordinatesByCompanyId(ClusterCreateCommand command, Long companyId);
	List<GeoCoordinateDetails> findCoordinatesDetailsByCompanyId(ClusterCreateCommand command, Long companyId);
}
