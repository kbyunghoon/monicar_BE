package org.collector.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PartitionFinder {

	private final ConsumerFactory<String, Object> consumerFactory;

	public String[] partitions(String topic) {
		try (Consumer<String, Object> consumer = consumerFactory.createConsumer()) {
			return consumer.partitionsFor(topic).stream()
				.map(pi -> "" + pi.partition())
				.toArray(String[]::new);
		}
	}

}
