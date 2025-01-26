package org.controlcenter.common.util;

import org.controlcenter.common.exception.TokenValidationException;
import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenValidator {

	private final RedisUtil redisUtil;
	private final JWTUtil jwtUtil;
	
	private static final ErrorCode EXPIRED_ACCESS_TOKEN_ERROR = ErrorCode.EXPIRED_ACCESS_TOKEN;
	private static final ErrorCode INVALID_ACCESS_TOKEN_ERROR = ErrorCode.INVALID_ACCESS_TOKEN;
	private static final ErrorCode EXPIRED_REFRESH_TOKEN_ERROR = ErrorCode.EXPIRED_REFRESH_TOKEN;
	private static final ErrorCode INVALID_REFRESH_TOKEN_ERROR = ErrorCode.INVALID_REFRESH_TOKEN;
	private static final ErrorCode EXPIRED_TOKENS_ERROR = ErrorCode.EXPIRED_TOKENS;
	private static final ErrorCode FORBIDDEN_ERROR = ErrorCode.FORBIDDEN_ACCESS;

	/**
	 * AccessToken, RefreshToken 만료 / 유효성 / 블랙리스트 등을 종합 검증
	 */
	public void validateTokens(String accessToken, String refreshToken) {
		if (redisUtil.isAccessTokenBlacklisted(accessToken)) {
			throw new TokenValidationException(FORBIDDEN_ERROR);
		}

		boolean isAccessTokenExpired;
		try {
			isAccessTokenExpired = jwtUtil.isExpiredStrict(accessToken);
		} catch (JwtException e) {
			throw new TokenValidationException(INVALID_ACCESS_TOKEN_ERROR);
		}

		boolean isRefreshTokenExpired;
		try {
			isRefreshTokenExpired = jwtUtil.isExpiredStrict(refreshToken);
		} catch (JwtException e) {
			throw new TokenValidationException(INVALID_REFRESH_TOKEN_ERROR);
		}

		if (isAccessTokenExpired && isRefreshTokenExpired) {
			throw new TokenValidationException(EXPIRED_TOKENS_ERROR);
		}
		if (isRefreshTokenExpired) {
			throw new TokenValidationException(EXPIRED_REFRESH_TOKEN_ERROR);
		}
		if (isAccessTokenExpired) {
			throw new TokenValidationException(EXPIRED_ACCESS_TOKEN_ERROR);
		}
	}

	/**
	 * AccessToken에서 userId 추출
	 */
	public String extractUserIdFromAccessToken(String accessToken) {
		return jwtUtil.validateAndGetId(accessToken);
	}
}
