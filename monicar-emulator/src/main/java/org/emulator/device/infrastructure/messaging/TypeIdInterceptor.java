package org.emulator.device.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class TypeIdInterceptor implements ProducerInterceptor<String, Object> {
	private static final String TYPE_ID_KEY = "__TypeId__";
	private static final String CONSUMER_CLASS_PACKAGE_PATH = "org.collector.presentation.dto.CycleInfoRequest";

	@Override
	public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> producerRecord) {
		producerRecord.headers().add(
			TYPE_ID_KEY,
			CONSUMER_CLASS_PACKAGE_PATH.getBytes(StandardCharsets.UTF_8)
		);
		return producerRecord;
	}

	@Override
	public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
		// method not used
	}

	@Override
	public void close() {
		// method not used
	}

	@Override
	public void configure(Map<String, ?> map) {
		// method not used
	}
}
