package org.controlcenter.company.presentation.swagger;

import io.swagger.v3.oas.annotations.Hidden;

import jakarta.validation.Valid;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.security.CustomUserDetails;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.presentation.dto.LoginRequest;
import org.controlcenter.company.presentation.dto.SignUpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "사용자 기본 API", description = "사용자 관련 기본 기능 API")
public interface ManagerApi {
	@Hidden
	BaseResponse<Void> signup(@Valid @RequestBody SignUpRequest signUpRequest);

	@Operation(summary = "로그인 기능",
		description = "<h1>로그인 기능</h1>")
	@PostMapping("/sign-in")
	default void login(@RequestParam String userId, @RequestParam String password) {
	}

	@Operation(summary = "사용자 정보 조회",
		description = "클라이언트에 보여질 사용자 정보")
	@PreAuthorize("hasRole('ROLE_USER')")
	BaseResponse<ManagerInformation> getProfile(
		@AuthenticationPrincipal CustomUserDetails userPrincipal
	);
}