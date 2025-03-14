package org.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.collector.application.CycleInfoService;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CycleInfoConsumer {
	private final CycleInfoService cycleInfoService;

	@KafkaListener(topicPartitions = @TopicPartition(topic = "cycleInfo-json-topic",
		partitions = "#{@finder.partitions('cycleInfo-json-topic')}"))
	public void accept(ConsumerRecord<String, CycleInfoRequest> message) {
		cycleInfoService.cycleInfoSave(message.value());
		log.info("[Main Consumer] Message arrived! - " + message.key());
	}
}
