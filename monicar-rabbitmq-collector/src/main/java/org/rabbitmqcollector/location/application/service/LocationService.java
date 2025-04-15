package org.rabbitmqcollector.location.application.service;

import java.util.List;

import org.rabbitmqcollector.location.application.port.VehicleRepository;
import org.rabbitmqcollector.location.application.port.out.LocationJpaRepository;
import org.rabbitmqcollector.location.application.port.out.LocationPublisherPort;
import org.rabbitmqcollector.location.application.port.out.LocationRedisRepository;
import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;
import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

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
		redisRepository.pushHistory(carId, message);

		List<CarLocationMessage> history = redisRepository.getHistory(carId);

		if (history.size() >= 60) {
			publisherPort.publishLocation(carId, history);

			List<CycleInfoEntity> entities = history.stream()
				.map(CycleInfoEntity::from)
				.toList();

			locationJpaRepository.saveAll(entities);

			var vehicle = vehicleRepository.findVehicleById(carId);
			CarLocationMessage last = history.getLast();
			int lastLat = last.lat();
			int lastLng = last.lng();

			if (vehicle.getStatus() == VehicleStatus.NOT_DRIVEN) {
				vehicle.updateVehicleStatusAndLocation(lastLat, lastLng);
			} else {
				vehicle.updateVehicleLocation(lastLat, lastLng);
			}

			redisRepository.clearHistory(String.valueOf(carId));
		}
	}
}
