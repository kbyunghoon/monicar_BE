package org.controlcenter.common.util;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.exception.TokenValidationException;
import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenValidator {

	private final RedisUtil redisUtil;
	private final JWTUtil jwtUtil;

	private static final ErrorCode EXPIRED_ACCESS_TOKEN_ERROR = ErrorCode.EXPIRED_ACCESS_TOKEN;
	private static final ErrorCode EXPIRED_REFRESH_TOKEN_ERROR = ErrorCode.EXPIRED_REFRESH_TOKEN;
	private static final ErrorCode FORBIDDEN_ERROR = ErrorCode.FORBIDDEN_ACCESS;

	/**
	 * AccessToken, RefreshToken 만료 / 유효성 / 블랙리스트 등을 종합 검증
	 */
	public void validateTokens(String accessToken, String refreshToken) {
		if (refreshToken == null) {
			throw new BusinessException(FORBIDDEN_ERROR);
		}

		if (!redisUtil.getRefreshToken(extractUserIdFromAccessToken(refreshToken)).equals(refreshToken)) {
			throw new BusinessException(FORBIDDEN_ERROR);
		}

		if (!jwtUtil.isValidTokenIgnoreExpiration(refreshToken)) {
			throw new BusinessException(FORBIDDEN_ERROR);
		}

		if (accessToken != null && !jwtUtil.isValidTokenIgnoreExpiration(accessToken)) {
			throw new BusinessException(FORBIDDEN_ERROR);
		}

		boolean accessExpired = (accessToken == null) || jwtUtil.isExpiredStrict(accessToken);
		boolean refreshExpired = jwtUtil.isExpiredStrict(refreshToken);

		if (!accessExpired && !refreshExpired) {
			return;
		}

		if (!accessExpired) {
			throw new TokenValidationException(EXPIRED_REFRESH_TOKEN_ERROR);
		}

		if (!refreshExpired) {
			throw new TokenValidationException(EXPIRED_ACCESS_TOKEN_ERROR);
		}

		throw new BusinessException(FORBIDDEN_ERROR);
	}

	/**
	 * AccessToken에서 userId 추출
	 */
	public String extractUserIdFromAccessToken(String accessToken) {
		return jwtUtil.validateAndGetId(accessToken);
	}
}
