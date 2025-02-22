package org.emulator.sensor;

import lombok.Getter;

import org.emulator.sensor.dto.Gps;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class LocationHolder {
	private final Queue<Gps> gpsDataQueue = new ConcurrentLinkedQueue<>();
	@Getter
	private volatile Gps gpsDataLatest;

	public void addGpsData(Gps newGps) {
		gpsDataQueue.offer(newGps);
		updateGpsDataLatest(newGps);
	}

	public Gps getGpsData() {
		return gpsDataQueue.poll();
	}

	private void updateGpsDataLatest(Gps newGps) {
		gpsDataLatest = newGps;
	}
}
