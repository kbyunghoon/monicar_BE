package org.emulator.device.infrastructure.util;


public class Calculator {
	private static final double EARTH_RADIUS = 6371000.0;
	private static final int DISTANCE_MAX = 9_999_999;
	private static final int DIRECTION_MAX = 365;
	private static final int SPEED_MAX = 255;
	private static final int SPEED_MIN = 0;
	private static final double MILLIS_TO_SECONDS_SCALE = 1000.0;
	/**
	 * 스케일링을 위한 최대 예상 속도 (자동차의 최고 속도인 약 70 m/s -> 252 km/h)
	 *
	 * @value 70.0
	 */
	public static final double EXPECTED_MAX_SPEED_MPS = 70.0;

	/**
	 * Haversine 공식 (지구 표면상 두 점 사이의 최단 거리를 구하는 공식) 을 사용하여 두 지점 간의 거리를 계산
	 * @return 이동 거리 (미터, 0 ~ 9,999,999)
	 */
	public static int calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		if (Double.compare(lat1, lat2) == 0 && Double.compare(lon1, lon2) == 0) {
			return 0;
		}
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
			+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(radLat1) * Math.cos(radLat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distance = EARTH_RADIUS * c;
		return (int) Math.min(distance, DISTANCE_MAX);
	}

	/**
	 * 방위각(북쪽을 기준으로 시계 방향으로 측정된 각도) 계산 공식을 사용하여 두 지점 간의 방향을 계산
	 * @return 방위각 (도, 0 ~ 365)
	 */
	public static int calculateDirection(double lat1, double lon1, double lat2, double lon2) {
		if (Double.compare(lat1, lat2) == 0 && Double.compare(lon1, lon2) == 0) {
			return 0;
		}
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double deltaLon = Math.toRadians(lon2 - lon1);

		double y = Math.sin(deltaLon) * Math.cos(radLat2);
		double x = Math.cos(radLat1) * Math.sin(radLat2) -
			Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(deltaLon);
		double bearing = Math.toDegrees(Math.atan2(y, x));
		bearing = (bearing + 360) % 360;

		return (int) Math.min(bearing, DIRECTION_MAX);
	}

	/**
	 * 이동 거리와 시간 차이를 기반으로 속도를 계산하고, 이를 0 ~ 255 범위로 스케일링합니다.
	 * @param distance 이동 거리 (미터)
	 * @param timeMillis    시간 차이 (밀리 초)
	 * @return 스케일된 속도 (0 ~ 255)
	 */
	public static int calculateSpeed(int distance, long timeMillis) {
		if (timeMillis <= 0L) {
			return 0;
		}
		double timeSeconds = timeMillis / MILLIS_TO_SECONDS_SCALE;
		double speedMps = distance / timeSeconds;

		double expectedMaxSpeed = EXPECTED_MAX_SPEED_MPS;
		int speed = (int) ((speedMps / expectedMaxSpeed) * SPEED_MAX);
		return Math.clamp(speed, SPEED_MIN, SPEED_MAX);
	}

	private Calculator() {
		throw new IllegalAccessError("Utility class");
	}
}
