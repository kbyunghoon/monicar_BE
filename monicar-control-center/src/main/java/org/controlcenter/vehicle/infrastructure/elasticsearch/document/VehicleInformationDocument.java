package org.controlcenter.vehicle.infrastructure.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = "vehicle-information")
@Setting(settingPath = "/elasticsearch/vehicle_information_settings.json")
@Mapping(mappingPath = "/elasticsearch/vehicle_information_mappings.json")
public class VehicleInformationDocument {
	@Id
	private long id;

	private String vehicleNumber;
}
