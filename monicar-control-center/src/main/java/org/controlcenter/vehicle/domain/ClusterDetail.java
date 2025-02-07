package org.controlcenter.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClusterDetail {
	private long vehicleId;
	private String vehicleNumber;
	private Integer lat;
	private Integer lng;
}
