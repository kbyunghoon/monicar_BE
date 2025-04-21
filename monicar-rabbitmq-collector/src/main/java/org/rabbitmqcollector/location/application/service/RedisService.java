package org.rabbitmqcollector.location.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.presentation.dto.CarLocationSocketMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	public List<CycleInfo> fetchAll() {
		Set<String> keys = redisTemplate.keys("car:history:*");
		if (keys.isEmpty())
			return Collections.emptyList();

		List<CycleInfo> entities = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();

		for (String key : keys) {
			List<String> histories = redisTemplate.opsForList().range(key, 0, -1);
			if (histories == null || histories.isEmpty())
				continue;

			for (String raw : histories) {
				try {
					CarLocationSocketMessage msg = objectMapper.readValue(raw, CarLocationSocketMessage.class);
					CycleInfo entity = CarLocationSocketMessage.toDomain(msg);
					entities.add(entity);
				} catch (Exception e) {
					log.warn("[파싱 실패] key={}, value={}", key, raw, e);
				}
			}

			redisTemplate.delete(key);
		}

		return entities;
	}
}
