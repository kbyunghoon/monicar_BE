package org.controlcenter.company.application;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final CookieUtil cookieUtil;

	@Value("${jwt.expiration.access}")
	private Long accessExpiration;

	@Value("${jwt.expiration.refresh}")
	private Long refreshExpiration;

	/**
	 * Refresh Token으로 Access Token 갱신 로직
	 */
	public String refreshAccessToken(String accessToken, String refreshToken) {
		if (accessToken != null) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		String userId;
		try {
			userId = jwtUtil.validateAndGetId(refreshToken);
		} catch (JwtException e) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		String savedRefreshToken = redisUtil.getRefreshToken(userId);
		if (!refreshToken.equals(savedRefreshToken)) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		return jwtUtil.createJwt(userId, accessExpiration, "access");
	}

	/**
	 * Access Token으로 Refresh Token 갱신 로직
	 */
	public String reissueRefreshToken(String accessToken, String oldRefreshToken) {
		if (oldRefreshToken == null) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		try {
			jwtUtil.isExpiredStrict(oldRefreshToken);
		} catch (JwtException e) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		String userId;
		try {
			userId = jwtUtil.validateAndGetId(accessToken);
		} catch (JwtException e) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		boolean isIncludeBlacklist = redisUtil.isAccessTokenBlacklisted(accessToken);
		if (isIncludeBlacklist) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		String newRefreshToken = jwtUtil.createJwt(userId, refreshExpiration, "refresh");
		redisUtil.saveRefreshToken(userId, newRefreshToken, refreshExpiration + accessExpiration);

		return newRefreshToken;
	}
}
