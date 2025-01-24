package org.controlcenter.common.security;

import java.io.IOException;
import java.util.List;

import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.util.JWTUtil;
import org.controlcenter.util.RedisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final RedisUtil redisUtil;
	private final JWTUtil jwtUtil;
	private final CustomUserDetailService customUserDetailService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final List<String> EXCLUDED_URIS = List.of(
		"/auth/refresh",
		"/api/v1/sign-in",
		"/api/v1/sign-up",
		"/auth/reissue"
	);

	private static final ErrorCode EXPIRED_REFRESH_TOKEN_ERROR = ErrorCode.EXPIRED_REFRESH_TOKEN;
	private static final ErrorCode EXPIRED_ACCESS_TOKEN_ERROR = ErrorCode.EXPIRED_ACCESS_TOKEN;
	private static final ErrorCode INVALID_ACCESS_TOKEN_ERROR = ErrorCode.INVALID_ACCESS_TOKEN;
	private static final ErrorCode INVALID_REFRESH_TOKEN_ERROR = ErrorCode.INVALID_REFRESH_TOKEN;
	private static final ErrorCode EXPIRED_TOKENS = ErrorCode.EXPIRED_TOKENS;
	private static final ErrorCode EXPIRED_ACCESS_TOKEN = ErrorCode.EXPIRED_ACCESS_TOKEN;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		if (isExcludedUri(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractAccessTokenFromCookies(request);
		String refreshToken = extractRefreshTokenFromCookies(request);

		if (accessToken != null && redisUtil.isAccessTokenBlacklisted(accessToken)) {
			writeErrorResponse(response, EXPIRED_ACCESS_TOKEN);
			return;
		}

		if (accessToken != null && refreshToken != null
			&& jwtUtil.isExpired(accessToken) && jwtUtil.isExpired(refreshToken)) {
			writeErrorResponse(response, EXPIRED_TOKENS);
			return;
		}

		if (accessToken != null && refreshToken != null && jwtUtil.isExpired(refreshToken)) {
			writeErrorResponse(response, EXPIRED_REFRESH_TOKEN_ERROR);
			return;
		}

		if (accessToken != null) {
			try {
				String userId = jwtUtil.validateAndGetId(accessToken);
				setAuthentication(userId);
			} catch (ExpiredJwtException e) {
				writeErrorResponse(response, EXPIRED_ACCESS_TOKEN_ERROR);
				return;
			} catch (JwtException e) {
				writeErrorResponse(response, INVALID_ACCESS_TOKEN_ERROR);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private boolean isExcludedUri(String uri) {
		return EXCLUDED_URIS.contains(uri);
	}

	private String extractAccessTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return null;
		}

		for (Cookie cookie : request.getCookies()) {
			if ("access_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	private String extractRefreshTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return null;
		}

		for (Cookie cookie : request.getCookies()) {
			if ("refresh_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	private void setAuthentication(String userId) {
		CustomUserDetails userDetails =
			(CustomUserDetails)customUserDetailService.loadUserByUsername(userId);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private void writeErrorResponse(HttpServletResponse response,
		ErrorCode errorCode) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		CustomAuthenticationFailureHandler.FailResponse failResponse =
			new CustomAuthenticationFailureHandler.FailResponse(
				errorCode.getCode(),
				false,
				List.of(errorCode.getMessage())
			);

		response.getWriter().write(objectMapper.writeValueAsString(failResponse));
	}
}
