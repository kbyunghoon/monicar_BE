package org.controlcenter.common.security;

import java.io.IOException;

import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.controlcenter.company.application.ManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final CookieUtil cookieUtil;
	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ManagerService managerService;

	@Value("${jwt.expiration.access}")
	private Long accessExpiration;

	@Value("${jwt.expiration.refresh}")
	private Long refreshExpiration;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		String userId = authentication.getName();
		managerService.updateLastLoginAt(userId);

		String accessToken = jwtUtil.createJwt(userId, accessExpiration, "access");

		String refreshToken = jwtUtil.createJwt(userId, refreshExpiration, "refresh");

		redisUtil.saveRefreshToken(userId, refreshToken, refreshExpiration + accessExpiration);

		response.addHeader("Set-Cookie", cookieUtil.createAccessTokenCookie(accessToken, accessExpiration).toString());
		response.addHeader("Set-Cookie",
			cookieUtil.createRefreshTokenCookie(refreshToken, refreshExpiration + accessExpiration).toString());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(
			new SuccessResponse(true, "로그인에 성공하였습니다.")
		));
		response.setStatus(HttpStatus.OK.value());
	}

	public record SuccessResponse(boolean isSuccess, String message) {
	}
}
