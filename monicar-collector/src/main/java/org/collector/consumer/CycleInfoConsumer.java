package org.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.collector.application.CycleInfoService;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CycleInfoConsumer {
	private final CycleInfoService cycleInfoService;

	@KafkaListener(
		topics = { "cycleInfo-json-topic" },
		groupId = "consumer-group"
	)
	public void accept(ConsumerRecord<String, CycleInfoRequest> message) {
		System.out.println("[Main Consumer] Message arrived! - " + message.value());
		cycleInfoService.cycleInfoSave(message.value());
	}
}
