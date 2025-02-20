package org.eventhub.config;

import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PartitionFinder {

	private final ProducerFactory<String, Object> producerFactory;

	public String[] partitions(String topic) {
		try (Producer<String, Object> producer = producerFactory.createProducer()) {
			return producer.partitionsFor(topic).stream()
				.map(pi -> "" + pi.partition())
				.toArray(String[]::new);
		}
	}

}
