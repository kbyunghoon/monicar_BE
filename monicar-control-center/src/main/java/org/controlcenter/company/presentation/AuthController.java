package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Hidden
public class AuthController {
	@GetMapping("/loginFail")
	public BaseResponse<Void> loginFail() {
		return BaseResponse.fail(ErrorCode.LOGIN_FAILED);
	}

	@GetMapping("/logoutOk")
	public BaseResponse<Void> logoutOk() {
		return BaseResponse.success();
	}
}
