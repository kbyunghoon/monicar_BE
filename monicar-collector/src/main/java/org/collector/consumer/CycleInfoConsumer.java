package org.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.collector.application.CycleInfoService;
import org.collector.common.constant.CycleInfoSize;
import org.collector.common.exception.CustomException;
import org.collector.common.response.ResponseCode;
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
		if(message.value().cList().size() != CycleInfoSize.CYCLE_INFO_MAX_SIZE.getSize()){
			throw new CustomException(ResponseCode.NOT_CYCLE_INFO_SIZE_ERROR);
		}
		System.out.println("[Main Consumer] Message arrived! - " + message.value());
		cycleInfoService.cycleInfoSave(message.value());
	}
}
