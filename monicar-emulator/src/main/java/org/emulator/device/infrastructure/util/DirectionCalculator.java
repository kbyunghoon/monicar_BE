package org.emulator.device.infrastructure.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DirectionCalculator implements MovementCalculator {
	private static final int FULL_CIRCLE_DEGREES = 360;
	private static final int EQUAL = 0;
	private static final int DIRECTION_MIN = 0;
	private static final int DIRECTION_MAX = 365;
	private static final int LAT_LON_SCALE = 1_000_000;

	/**
	 * 방위각(북쪽을 기준으로 시계 방향으로 측정된 각도) 계산 공식을 사용하여 두 지점 간의 방향을 계산
	 * @return 방위각 (도, 0 ~ 365)
	 */
	@Override
	public Integer calculate(CycleInfo preInfo, GpsTime curInfo) {
		BigDecimal scale = BigDecimal.valueOf(LAT_LON_SCALE);
		BigDecimal bigDecimalLat1 = BigDecimal.valueOf(preInfo.getGeo().getLatitude()).divide(scale, 10, RoundingMode.HALF_UP);
		BigDecimal bigDecimalLon1 = BigDecimal.valueOf(preInfo.getGeo().getLongitude()).divide(scale, 10, RoundingMode.HALF_UP);

		double lat1 = bigDecimalLat1.doubleValue();
		double lon1 = bigDecimalLon1.doubleValue();
		double lat2 = curInfo.location().lat();
		double lon2 = curInfo.location().lon();

		if (Double.compare(lat1, lat2) == EQUAL && Double.compare(lon1, lon2) == EQUAL) {
			return DIRECTION_MIN;
		}
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double deltaLon = Math.toRadians(lon2 - lon1);

		double y = Math.sin(deltaLon) * Math.cos(radLat2);
		double x = Math.cos(radLat1) * Math.sin(radLat2) -
			Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(deltaLon);
		double bearing = Math.toDegrees(Math.atan2(y, x));
		bearing = (bearing + FULL_CIRCLE_DEGREES) % FULL_CIRCLE_DEGREES;

		return (int) Math.min(bearing, DIRECTION_MAX);
	}
}
