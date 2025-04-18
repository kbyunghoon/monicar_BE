package org.rabbitmqcollector.location.application.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.rabbitmqcollector.location.application.port.VehicleRepository;
import org.rabbitmqcollector.location.application.port.out.LocationJpaRepository;
import org.rabbitmqcollector.location.application.port.out.LocationPublisherPort;
import org.rabbitmqcollector.location.application.port.out.LocationRedisRepository;
import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;
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
	private final LocationJpaRepository locationJpaRepository;
	private final VehicleRepository vehicleRepository;

	@Transactional
	public void handleLocation(CarLocationMessage message) {
		long carId = message.id();
		int lat = message.lat();
		int lng = message.lng();

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(message.timestamp(), now);

		var cycleInfo = CarLocationMessage.toDomain(message);

		locationJpaRepository.save(CycleInfoEntity.from(cycleInfo));

		var vehicle = vehicleRepository.findVehicleById(carId);

		if (vehicle.getStatus() == VehicleStatus.NOT_DRIVEN) {
			vehicle.updateVehicleStatusAndLocation(lat, lng);
		}

		if (diff.getSeconds() > 1) {
			log.warn("[무시됨] 너무 오래된 메시지 ({}초 전)", diff.getSeconds());
			return;
		}

		var vehicleNumber = vehicleRepository.findVehicleIdByVehicleNumber(carId);
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
