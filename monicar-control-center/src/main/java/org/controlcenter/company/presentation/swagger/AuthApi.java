package org.controlcenter.company.presentation.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "토큰 재발급 API", description = "사용자 관련 토큰 재발급 기능 API")
public interface AuthApi {

	@Operation(summary = "액세스 토큰 재발급 API",
		description = "리프레시 토큰으로 액세스 토큰 재발급")
	ResponseEntity<?> refreshAccessToken(
		@Parameter(hidden = true) @CookieValue(value = "access_token", required = false) String accessToken,
		@Parameter(hidden = true) @CookieValue("refresh_token") String refreshToken,
		HttpServletResponse response
	);

	@Operation(summary = "리프레시 토큰 재발급 API",
		description = "액세스 토큰으로 리프레시 토큰 재발급")
	ResponseEntity<?> reissueRefreshToken(
		@Parameter(hidden = true) @CookieValue("access_token") String accessToken,
		@Parameter(hidden = true) @CookieValue(value = "refresh_token", required = false) String oldRefreshToken,
		HttpServletResponse response
	);
}
