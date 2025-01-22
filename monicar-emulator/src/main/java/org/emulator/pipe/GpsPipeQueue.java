package org.emulator.pipe;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpsPipeQueue {

	@Bean
	public ConcurrentLinkedQueue<Gps> gpsConcurrentLinkedQueue() {
		return new ConcurrentLinkedQueue<>();
	}

	@Bean
	public LinkedBlockingDeque<Gps> gpsDataQueue() {
		return new LinkedBlockingDeque<>(5);
	}
}
