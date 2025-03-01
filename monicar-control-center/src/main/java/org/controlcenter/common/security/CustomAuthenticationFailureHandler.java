package org.controlcenter.common.security;

import java.io.IOException;
import java.util.List;

import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		ErrorCode errorCode = ErrorCode.LOGIN_FAILED;

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(
			new CustomAuthenticationFailureHandler.FailResponse(errorCode.getCode(), false,
				List.of(errorCode.getMessage()))
		));
	}

	protected record FailResponse(int errorCode, boolean isSuccess, List<String> errorMessage) {
	}
}
