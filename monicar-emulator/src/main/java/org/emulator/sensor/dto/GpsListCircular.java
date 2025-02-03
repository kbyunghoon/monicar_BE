package org.emulator.sensor.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
public class GpsListCircular {
	private final List<Gps> gpsList;
	private int gpsListSize;
	private int interruptedIdx;

	public GpsListCircular() {
		gpsList = new ArrayList<>();
		gpsList.add(new Gps(20.111111, 30.111111));
		gpsListSize = gpsList.size();
		interruptedIdx = 0;
	}

	public void loadFileData(Queue<Gps> fileData) {
		gpsList.clear();
		gpsList.addAll(fileData);
		gpsListSize = gpsList.size();
	}

	public void add(Gps gps) {
		gpsList.add(gps);
	}

	public Gps getMovingGps() {
		Gps data = gpsList.get(interruptedIdx);
		interruptedIdx = getInfiniteIdx();
		return data;
	}

	private int getInfiniteIdx() {
		return (interruptedIdx + 1) % gpsListSize;
	}

	public Gps getStopGps() {
		return gpsList.get(interruptedIdx);
	}
}
