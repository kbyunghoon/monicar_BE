package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final CookieUtil cookieUtil;

	@Value("${jwt.expiration.access}")
	private Long accessExpiration;

	@Value("${jwt.expiration.refresh}")
	private Long refreshExpiration;

	/**
	 * Refresh Token으로 Access Token 갱신
	 */
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAccessToken(
		@Parameter(hidden = true) @CookieValue(value = "access-token", required = false) String accessToken,
		@Parameter(hidden = true) @CookieValue("refresh-token") String refreshToken,
		HttpServletResponse response
	) {
		if (accessToken != null) {
			return createErrorResponse();
		}

		String userId;
		try {
			userId = jwtUtil.validateAndGetId(refreshToken);
		} catch (JwtException e) {
			return createErrorResponse();
		}

		String savedRefreshToken = redisUtil.getRefreshToken(userId);
		if (!refreshToken.equals(savedRefreshToken)) {
			return createErrorResponse();
		}

		String newAccessToken = jwtUtil.createJwt(userId, accessExpiration, "access");
		response.addHeader("Set-Cookie",
			cookieUtil.createAccessTokenCookie(newAccessToken, accessExpiration).toString());

		return ResponseEntity.ok(BaseResponse.success());
	}

	/**
	 * Access Token으로  Refresh Token갱신
	 */
	@PostMapping("/reissue")
	public ResponseEntity<?> reissueRefreshToken(
		@Parameter(hidden = true) @CookieValue("access-token") String accessToken,
		@Parameter(hidden = true) @CookieValue(value = "refresh-token", required = false) String oldRefreshToken,
		HttpServletResponse response
	) {
		if (oldRefreshToken == null) {
			return createErrorResponse();
		}

		try {
			jwtUtil.isExpiredStrict(oldRefreshToken);
		} catch (JwtException e) {
			return createErrorResponse();
		}

		String userId;
		try {
			userId = jwtUtil.validateAndGetId(accessToken);
		} catch (JwtException e) {
			return createErrorResponse();
		}

		boolean isIncludeBlacklist = redisUtil.isAccessTokenBlacklisted(accessToken);
		if (isIncludeBlacklist) {
			return createErrorResponse();
		}

		String newRefreshToken = jwtUtil.createJwt(userId, refreshExpiration, "refresh");
		redisUtil.saveRefreshToken(userId, newRefreshToken, refreshExpiration + accessExpiration);

		response.addHeader("Set-Cookie",
			cookieUtil.createRefreshTokenCookie(newRefreshToken, refreshExpiration + accessExpiration).toString());

		return ResponseEntity.ok(BaseResponse.success("새로운 Refresh Token 발급 완료"));
	}

	private ResponseEntity<BaseResponse<?>> createErrorResponse() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.body(BaseResponse.fail(ErrorCode.FORBIDDEN_ACCESS));
	}
}