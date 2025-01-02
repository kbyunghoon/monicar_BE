package org.emulator.device.infrastructure;

import java.util.Objects;
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

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public Gps getLocation() {
		return Objects.requireNonNull(
			gpsPipeQueue.poll(),
			"No Gps data found. Check sensor"
		);
	}

	@Override
	public boolean readyToSend() {
		return false;
	}
}
