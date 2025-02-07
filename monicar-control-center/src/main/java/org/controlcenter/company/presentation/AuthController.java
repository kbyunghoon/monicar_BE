package org.controlcenter.company.presentation;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.company.application.AuthService;
import org.controlcenter.company.presentation.swagger.AuthApi;
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
public class AuthController implements AuthApi {

	private final AuthService authService;
	private final CookieUtil cookieUtil;

	@Value("${jwt.expiration.access}")
	private Long accessExpiration;

	@Value("${jwt.expiration.refresh}")
	private Long refreshExpiration;

	/**
	 * Refresh Token으로 Access Token 갱신
	 */
	@PostMapping("/reissue")
	public ResponseEntity<?> refreshAccessToken(
		@Parameter(hidden = true) @CookieValue(value = "access_token", required = false) String accessToken,
		@Parameter(hidden = true) @CookieValue("refresh_token") String refreshToken,
		HttpServletResponse response
	) {
		try {
			String newAccessToken = authService.refreshAccessToken(accessToken, refreshToken);

			response.addHeader("Set-Cookie",
				cookieUtil.createAccessTokenCookie(newAccessToken, accessExpiration).toString());

			return ResponseEntity.ok(BaseResponse.success());

		} catch (BusinessException | JwtException e) {
			return createErrorResponse(response);
		}
	}

	/**
	 * Access Token으로 Refresh Token 갱신
	 */
	@PostMapping("/refresh")
	public ResponseEntity<?> reissueRefreshToken(
		@Parameter(hidden = true) @CookieValue("access_token") String accessToken,
		@Parameter(hidden = true) @CookieValue(value = "refresh_token", required = false) String oldRefreshToken,
		HttpServletResponse response
	) {
		try {
			String newRefreshToken = authService.reissueRefreshToken(accessToken, oldRefreshToken);
			response.addHeader("Set-Cookie",
				cookieUtil.createRefreshTokenCookie(newRefreshToken, refreshExpiration + accessExpiration).toString());

			return ResponseEntity.ok(BaseResponse.success("새로운 Refresh Token 발급 완료"));

		} catch (BusinessException | JwtException e) {
			return createErrorResponse(response);
		}
	}

	private ResponseEntity<BaseResponse<?>> createErrorResponse(HttpServletResponse response) {
		response.addHeader("Set-Cookie", cookieUtil.createAccessTokenCookie("", 0L).toString());
		response.addHeader("Set-Cookie",
			cookieUtil.createRefreshTokenCookie("", 0L).toString());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.body(BaseResponse.fail(ErrorCode.FORBIDDEN_ACCESS));
	}
}
