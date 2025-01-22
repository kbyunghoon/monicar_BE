package org.emulator.device.infrastructure;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;
import org.emulator.pipe.Gps;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GpsPipeQueueReceiver implements LocationReceiver {
	private final ConcurrentLinkedQueue<Gps> gpsPipeQueue;
	private final LinkedBlockingDeque<Gps> recentGpsLinkedBlockingDeque;

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public GpsTime fetchLocationNow() {
		try {
			Gps gps = Objects.requireNonNull(gpsPipeQueue.poll());
			return GpsTime.create(gps);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NO_GPS_DATA_FOUND);
		}
	}

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public GpsTime fetchLocationRecent() {
		try {
			Gps gps = Objects.requireNonNull(recentGpsLinkedBlockingDeque.peekLast());
			return GpsTime.create(gps);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NO_GPS_DATA_FOUND);
		}
	}
}
