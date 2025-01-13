package org.emulator.device.infrastructure.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DistanceCalculator implements MovementCalculator {
	private static final double EARTH_RADIUS = 6371000.0;
	private static final int EQUAL = 0;
	private static final int DISTANCE_MIN = 0;
	private static final int DISTANCE_MAX = 9_999_999;
	private static final int LAT_LON_SCALE = 1_000_000;

	/**
	 * Haversine 공식 (지구 표면상 두 점 사이의 최단 거리를 구하는 공식) 을 사용하여 두 지점 간의 거리를 계산
	 * @return 이동 거리 (미터, 0 ~ 9,999,999)
	 */
	@Override
	public Integer calculate(CycleInfo preInfo, GpsTime curInfo) {
		BigDecimal scale = BigDecimal.valueOf(LAT_LON_SCALE);
		BigDecimal bigDecimalLat1 = BigDecimal.valueOf(preInfo.getGeo().getLatitude())
			.divide(scale, 10, RoundingMode.HALF_UP);
		BigDecimal bigDecimalLon1 = BigDecimal.valueOf(preInfo.getGeo().getLongitude())
			.divide(scale, 10, RoundingMode.HALF_UP);

		double lat1 = bigDecimalLat1.doubleValue();
		double lon1 = bigDecimalLon1.doubleValue();
		double lat2 = curInfo.location().lat();
		double lon2 = curInfo.location().lng();

		if (Double.compare(lat1, lat2) == EQUAL && Double.compare(lon1, lon2) == EQUAL) {
			return DISTANCE_MIN;
		}
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(radLat1) * Math.cos(radLat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distance = EARTH_RADIUS * c;
		return (int)Math.min(distance, DISTANCE_MAX);
	}
}
