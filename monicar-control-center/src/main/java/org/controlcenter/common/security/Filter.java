package org.controlcenter.common.security;

import java.io.IOException;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

	private static final List<String> EXCLUDED_URIS = List.of(
		"/auth/refresh",
		"/api/v1/sign-in",
		"/api/v1/sign-up",
		"/auth/reissue",
		"/api/v1/logout"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		System.out.printf("요청 엔드포인트: %s / %s\n", request.getRequestURL().toString(), request.getMethod());

		filterChain.doFilter(request, response);
	}
}