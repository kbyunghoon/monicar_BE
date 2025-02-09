package org.controlcenter.vehicle.application;

import java.util.List;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.elasticsearch.document.VehicleInformationDocument;
import org.controlcenter.vehicle.infrastructure.elasticsearch.repository.VehicleInformationElasticsearchRepository;
import org.controlcenter.vehicle.infrastructure.elasticsearch.repository.VehicleSearchQueryRepository;
import org.controlcenter.vehicle.presentation.dto.SimpleVehicleInformationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleSearchService {
	private final VehicleInformationElasticsearchRepository vehicleInformationElasticsearchRepository;
	private final VehicleSearchQueryRepository vehicleSearchQueryRepository;

	@Transactional(readOnly = true)
	public List<SimpleVehicleInformationResponse> searchEventsByKeyword(String keyword) {
		return vehicleSearchQueryRepository.findByKeyword(keyword).stream().map(SimpleVehicleInformationResponse::from)
			.toList();
	}

	/**
	 * 예) JPA 엔티티를 저장/수정할 때 ES에 동기화하는 로직 (참고용)
	 */
	@Transactional
	public void syncVehicleToElasticsearch(VehicleInformation vehicleInformation) {
		VehicleInformationDocument doc = VehicleInformationDocument.builder()
			.id(vehicleInformation.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.build();
		vehicleInformationElasticsearchRepository.save(doc);
	}
}