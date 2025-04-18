package org.rabbitmqcollector.location.application.port.out;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.rabbitmqcollector.location.presentation.dto.CarLocationSocketMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRedisRepository {

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	private String historyKey(String carId) {
		return "car:history:" + carId;
	}

	public void pushHistory(long carId, CarLocationSocketMessage msg) {
		try {
			String json = objectMapper.writeValueAsString(msg);
			redisTemplate.opsForList().rightPush(historyKey(String.valueOf(carId)), json);
			redisTemplate.expire(historyKey(String.valueOf(carId)), Duration.ofMinutes(2));
		} catch (Exception e) {
			log.error("[Redis 저장 실패]", e);
		}
	}
	public void clearHistory(String carId) {
		redisTemplate.delete(historyKey(carId));
	}
}

