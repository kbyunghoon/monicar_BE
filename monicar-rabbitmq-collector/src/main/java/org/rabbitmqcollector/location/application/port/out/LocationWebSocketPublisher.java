package org.rabbitmqcollector.location.application.port.out;

import java.util.List;

import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationWebSocketPublisher implements LocationPublisherPort {

	private final SimpMessagingTemplate messagingTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void publishLocation(long carId, List<CarLocationMessage> history) {
		try {
			log.info("[WebSocket 전송 성공] - 차량번호 : {}", carId);
			String payload = objectMapper.writeValueAsString(history);
			messagingTemplate.convertAndSend("/topic/car/" + carId, payload);
		} catch (Exception e) {
			log.error("[WebSocket 전송 실패]", e);
		}
	}
}
