package org.emulator.device.application.port;

import org.emulator.pipe.Gps;

import java.util.List;

/**
 * 임의의 파일 or GPS 센서로부터 gps 정보를 받아온다.
 */
public interface LocationReceiver {
	Gps getLocation();

	boolean readyToSend();
}
