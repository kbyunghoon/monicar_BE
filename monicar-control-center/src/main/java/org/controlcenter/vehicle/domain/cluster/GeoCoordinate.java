package org.controlcenter.vehicle.domain.cluster;

import java.util.Objects;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

import lombok.Getter;

@Getter
public class GeoCoordinate {
	private static final int NORTH_EAST_KOREA_LAT = 38808478;  // 대한민국 우상단 위도
	private static final int NORTH_EAST_KOREA_LNG = 129939734; // 대한민국 우산단 경도
	private static final int SOUTH_WEST_KOREA_LAT = 32892556;  // 대한민국 좌하단 위도
	private static final int SOUTH_WEST_KOREA_LNG = 124579394; // 대한민국 좌하단 경도

	private final Integer lat;
	private final Integer lng;

	public GeoCoordinate(Integer lat, Integer lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public static GeoCoordinate of(int lat, int lng) {
		validateCoordinate(lat, lng);

		return new GeoCoordinate(lat, lng);
	}

	private static void validateCoordinate(Integer lat, Integer lng) {
		if (lat < SOUTH_WEST_KOREA_LAT || NORTH_EAST_KOREA_LAT < lat) {
			throw new BusinessException(ErrorCode.INVALID_TYPE_VALUE);
		}

		if (lng < SOUTH_WEST_KOREA_LNG || NORTH_EAST_KOREA_LNG < lng) {
			throw new BusinessException(ErrorCode.INVALID_TYPE_VALUE);
		}
	}


	public boolean isInBounds(GeoCoordinate boundsSw, GeoCoordinate boundsNe) {
		if (this.lat < boundsSw.lat || boundsNe.lat < this.lat) {
			return false;
		}
		if (this.lng < boundsSw.lng || boundsNe.lng < this.lng) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GeoCoordinate that = (GeoCoordinate)o;
		return Objects.equals(lat, that.lat) && Objects.equals(lng, that.lng);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lat, lng);
	}

	@Override
	public String toString() {
		return "GeoCoordinate{" +
			"lat=" + lat +
			", lng=" + lng +
			'}';
	}

}
