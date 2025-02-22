package org.controlcenter.common.security;

import java.io.IOException;
import java.util.List;

import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationErrorHandler {

	private final ObjectMapper objectMapper;

	public void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
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
