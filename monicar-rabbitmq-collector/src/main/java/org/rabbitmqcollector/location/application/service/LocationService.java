package org.rabbitmqcollector.location.application.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.rabbitmqcollector.location.application.port.CycleInfoRepository;
import org.rabbitmqcollector.location.application.port.LocationPublisherPort;
import org.rabbitmqcollector.location.application.port.LocationRedisRepository;
import org.rabbitmqcollector.location.application.port.VehicleEventRepository;
import org.rabbitmqcollector.location.application.port.VehicleInformationRepository;
import org.rabbitmqcollector.location.domain.VehicleEvent;
import org.rabbitmqcollector.location.domain.VehicleEventType;
import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.rabbitmqcollector.location.presentation.dto.CarLocationSocketMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
	private final LocationPublisherPort publisherPort;
	private final LocationRedisRepository redisRepository;
	private final VehicleInformationRepository vehicleInformationRepository;
	private final VehicleEventRepository vehicleEventRepository;
	private final CycleInfoRepository cycleInfoRepository;

	@Transactional
	public void handleLocation(CarLocationMessage message) {
		long carId = message.id();
		int lat = message.lat();
		int lng = message.lng();
		LocalDateTime timestamp = message.timestamp();

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(message.timestamp(), now);

		var cycleInfo = CarLocationMessage.toDomain(message);

		cycleInfoRepository.save(cycleInfo);

		var vehicle = vehicleInformationRepository.findVehicleById(carId);

		if (vehicle.getStatus() == VehicleStatus.NOT_DRIVEN) {
			vehicle.updateVehicleStatusAndLocation(lat, lng);

			var vehicleEvent = VehicleEvent.builder()
				.vehicleId(carId)
				.type(VehicleEventType.ON)
				.eventAt(timestamp)
				.build();

			vehicleEventRepository.save(vehicleEvent);
		} else {
			vehicle.updateVehicleLocation(lat, lng);
		}

		if (diff.getSeconds() > 1) {
			log.warn("[무시됨] 너무 오래된 메시지 ({}초 전)", diff.getSeconds());
			return;
		}

		var vehicleNumber = vehicleInformationRepository.findVehicleIdByVehicleNumber(carId);
		var socketMessage = CarLocationSocketMessage.builder()
			.id(carId)
			.vehicleNumber(vehicleNumber)
			.lat(lat)
			.lng(lng)
			.timestamp(now)
			.build();

		redisRepository.pushHistory(carId, socketMessage);
		publisherPort.publishLocation(message.id(), socketMessage);
	}
}
