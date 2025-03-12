package org.controlcenter.common.security;

import java.io.IOException;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NoSessionAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		response.setContentType("application/json;charset=UTF-8");

		BaseResponse<Void> baseResponse = BaseResponse.fail(ErrorCode.LOGIN_FAILED);
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(baseResponse);

		response.getWriter().write(jsonResponse);
	}
}
