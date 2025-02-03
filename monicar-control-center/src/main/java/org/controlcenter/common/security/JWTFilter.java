package org.controlcenter.common.security;

import java.io.IOException;
import java.util.List;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.exception.TokenValidationException;
import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.common.util.JWTTokenValidator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final CookieUtil cookieUtil;
	private final JWTTokenValidator jwtTokenValidator;
	private final CustomAuthenticationErrorHandler errorHandler;
	private final CustomUserDetailService customUserDetailService;

	private static final List<String> EXCLUDED_URIS = List.of(
		"/auth/refresh",
		"/api/v1/sign-in",
		"/auth/reissue",
		"/api/v1/logout",
		"/swagger-ui/index.html"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		StringBuilder fullURL = new StringBuilder(request.getRequestURL().toString());

		String queryString = request.getQueryString();
		if (queryString != null) {
			fullURL.append('?').append(queryString);
		}

		System.out.printf("요청 엔드포인트: %s / %s\n", fullURL, request.getMethod());

		if (isExcludedUri(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractAccessTokenFromCookies(request);
		String refreshToken = extractRefreshTokenFromCookies(request);

		if (accessToken == null && refreshToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			jwtTokenValidator.validateTokens(accessToken, refreshToken);

			String userId = jwtTokenValidator.extractUserIdFromAccessToken(accessToken);
			setAuthentication(userId);
		} catch (TokenValidationException e) {
			errorHandler.writeErrorResponse(response, e.getErrorCode());
			return;
		} catch (BusinessException e) {
			response.addHeader("Set-Cookie", cookieUtil.createAccessTokenCookie("", 0L).toString());
			response.addHeader("Set-Cookie",
				cookieUtil.createRefreshTokenCookie("", 0L).toString());
			errorHandler.writeErrorResponse(response, e.getErrorCode());
			return;

		}

		filterChain.doFilter(request, response);
	}

	private boolean isExcludedUri(String uri) {
		return EXCLUDED_URIS.contains(uri);
	}

	private String extractAccessTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null)
			return null;
		for (Cookie cookie : request.getCookies()) {
			if ("access_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	private String extractRefreshTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null)
			return null;
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
			new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
			);

		SecurityContextHolder.getContext()
			.setAuthentication(authenticationToken);
	}
}
