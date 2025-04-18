package org.rabbitmqcollector.location.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationBroadcastScheduler {

	private final RedisTemplate<String, String> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;

	@Scheduled(fixedRate = 1000)
	public void broadcastBufferedLocations() {
		Set<String> keys = redisTemplate.keys("car:history:*");
		if (keys.isEmpty())
			return;

		List<String> payloads = new ArrayList<>();

		for (String key : keys) {
			String latest = redisTemplate.opsForList().rightPop(key);
			if (latest == null) {
				continue;
			}
			payloads.add(latest);
			redisTemplate.delete(key);
		}

		if (!payloads.isEmpty()) {
			messagingTemplate.convertAndSend("/topic/car/all", payloads);
			log.info("[웹소켓 전송] 전체 차량 → {}개 데이터", payloads.size());
		}
	}
}