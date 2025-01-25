package org.emulator.device.application.port;

import org.emulator.sensor.dto.GpsTime;

/**
 * 임의의 파일 or GPS 센서로부터 GPS 정보를 받아온다.
 *
 * @method fetchLocationNow sensor를 위해 gps 데이터를 가져온다
 * @method fetchLocationRecent 외부 요청을 처리하는 service를 위해 gps 데이터를 가져온다
 */
public interface LocationReceiver {
	GpsTime fetchLocationNow();
	GpsTime fetchLocationRecent();
}
