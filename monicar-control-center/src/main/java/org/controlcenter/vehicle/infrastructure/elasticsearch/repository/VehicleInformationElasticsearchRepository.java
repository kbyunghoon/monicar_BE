package org.controlcenter.vehicle.infrastructure.elasticsearch.repository;

import org.controlcenter.vehicle.infrastructure.elasticsearch.document.VehicleInformationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInformationElasticsearchRepository
	extends ElasticsearchRepository<VehicleInformationDocument, Long> {
}
