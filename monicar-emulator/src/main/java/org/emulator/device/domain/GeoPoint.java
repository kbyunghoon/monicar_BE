package org.emulator.device.domain;

import lombok.Builder;

@Builder
public class GeoPoint {
	private String geoPointId;
	private String updateStatus;
	private String geoGroupId;
	private String geoAlertType;
	private String geoRange;
	private String latitude;
	private String longitude;
	private String geoPointStartTime;
	private String geoPointStopTime;
	private String saveType;
}
