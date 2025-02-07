package org.controlcenter.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Cluster {
	private double lat;
	private double lng;
	private long count;
}
