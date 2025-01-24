package org.controlcenter.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisUtil {

	private final StringRedisTemplate template;
	private static final String ACCESS_TOKEN_PREFIX = "AT:";
	private static final String REFRESH_TOKEN_PREFIX = "RT:";

	public void saveAccessToken(String accessToken, Long expirationMillis) {
		String key = ACCESS_TOKEN_PREFIX + accessToken;
		ValueOperations<String, String> ops = template.opsForValue();
		ops.set(key, accessToken, expirationMillis, TimeUnit.MILLISECONDS);
	}

	public boolean isAccessTokenBlacklisted(String accessToken) {
		String key = ACCESS_TOKEN_PREFIX + accessToken;
		return Boolean.TRUE.equals(template.hasKey(key));
	}

	public void saveRefreshToken(String userId, String refreshToken, Long expirationMillis) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		ValueOperations<String, String> ops = template.opsForValue();
		ops.set(key, refreshToken, expirationMillis, TimeUnit.MILLISECONDS);
	}

	public String getRefreshToken(String userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		ValueOperations<String, String> ops = template.opsForValue();
		return ops.get(key);
	}

	public boolean isRefreshTokenValid(String userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		return Boolean.TRUE.equals(template.hasKey(key));
	}

	public void removeRefreshToken(String userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		template.delete(key);
	}
}