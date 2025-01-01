package org.emulator.device.infrastructure;

import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.AllArgsConstructor;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.pipe.Gps;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GpsPipeQueueReceiver implements LocationReceiver {
	private final ConcurrentLinkedQueue<Gps> gpsPipeQueue;

	@Retryable(maxAttempts = 10, backoff = @Backoff(delay = 3000))
	@Override
	public Gps getLocation() {
		Gps location = gpsPipeQueue.poll();
		if (location == null)
			throw new NullPointerException("No Gps data found. Check sensor");
		return location;
	}

	@Override
	public boolean readyToSend() {
		return false;
	}
}
