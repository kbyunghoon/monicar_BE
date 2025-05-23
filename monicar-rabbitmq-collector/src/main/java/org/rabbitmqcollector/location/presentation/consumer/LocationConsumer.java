package org.rabbitmqcollector.location.presentation.consumer;

import org.rabbitmqcollector.location.application.service.LocationService;
import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationConsumer {
	private final ObjectMapper objectMapper;
	private final LocationService locationService;

	@RabbitListener(queues = "${rabbitmq.queue.car-location}")
	public void handleMessage(String message) {
		try {
			CarLocationMessage msg = objectMapper.readValue(message, CarLocationMessage.class);
			locationService.handleLocation(msg);
		} catch (Exception e) {
			log.error("메시지 처리 실패", e);
		}
	}
}
