package org.controlcenter.vehicle.application;

import java.util.List;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.elasticsearch.document.VehicleInformationDocument;
import org.controlcenter.vehicle.infrastructure.elasticsearch.repository.VehicleInformationElasticsearchRepository;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleInformationMigrationService {

	private final VehicleInformationJpaRepository jpaRepository;
	private final VehicleInformationElasticsearchRepository esRepository;

	/**
	 * 기존 MySQL DB에 있는 모든 vehicle_information 레코드들을
	 * Elasticsearch로 마이그레이션
	 */
	@Transactional
	public void migrateAll() {
		List<VehicleInformation> entities = jpaRepository.findAll()
			.stream()
			.map(VehicleInformationEntity::toDomain)
			.toList();

		entities.forEach(entity -> {
			if (!esRepository.existsById(entity.getId())) {
				VehicleInformationDocument doc = toDocument(entity);
				esRepository.save(doc);
			}
		});
	}

	private VehicleInformationDocument toDocument(VehicleInformation vehicleInformation) {
		return VehicleInformationDocument.builder()
			.id(vehicleInformation.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.build();
	}
}