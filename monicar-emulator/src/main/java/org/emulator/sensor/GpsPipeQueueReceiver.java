package org.emulator.sensor;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;
import org.emulator.sensor.dto.Gps;
import org.emulator.sensor.dto.GpsTime;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GpsPipeQueueReceiver implements LocationReceiver {
	private final LocationHolder locationHolder;

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public GpsTime fetchLocationNow() {
		try {
			Gps gps = Objects.requireNonNull(locationHolder.getGpsData());
			return GpsTime.create(gps);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NO_GPS_DATA_FOUND);
		}
	}

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public GpsTime fetchLocationRecent() {
		try {
			Gps gps = Objects.requireNonNull(locationHolder.getGpsDataLatest());
			return GpsTime.create(gps);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NO_GPS_DATA_FOUND);
		}
	}
}
