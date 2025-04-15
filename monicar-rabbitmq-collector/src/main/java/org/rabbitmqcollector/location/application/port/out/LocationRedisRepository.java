package org.rabbitmqcollector.location.application.port.out;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRedisRepository {

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	private String historyKey(String carId) {
		return "car:history:" + carId;
	}

	public void pushHistory(long carId, CarLocationMessage msg) {
		try {
			String json = objectMapper.writeValueAsString(msg);
			redisTemplate.opsForList().rightPush(historyKey(String.valueOf(carId)), json);
			redisTemplate.expire(historyKey(String.valueOf(carId)), Duration.ofMinutes(2));
		} catch (Exception e) {
			log.error("[Redis 저장 실패]", e);
		}
	}

	public List<CarLocationMessage> getHistory(long carId) {
		List<String> messages = redisTemplate.opsForList().range(historyKey(String.valueOf(carId)), 0, -1);
		if (messages == null) return Collections.emptyList();

		return messages.stream().map(msg -> {
			try {
				return objectMapper.readValue(msg, CarLocationMessage.class);
			} catch (Exception e) {
				return null;
			}
		}).filter(Objects::nonNull).toList();
	}

	public void clearHistory(String carId) {
		redisTemplate.delete(historyKey(carId));
	}
}

