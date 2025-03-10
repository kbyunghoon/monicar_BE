package org.controlcenter.company.presentation.swagger;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.security.CustomUserDetails;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.presentation.dto.LoginRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "사용자 기본 API", description = "사용자 관련 기본 기능 API")
public interface ManagerApi {
	@Operation(summary = "로그인 기능",
		description = "<h1>로그인 기능</h1>"
			+ "<h2>액세스토큰과 리프레시토큰 유효할 경우</h2>"
			+ "정상 응답"
			+ "<h2>액세스토큰 만료된 경우(9995)</h2>"
			+ "9995 오류 코드 발생 시 <strong>'/reissue'로 요청</strong>하여 액세스토큰 재발급 받고 다시 요청하려던 엔드포인트로 재요청"
			+ "<h2>액세스토큰이 만료된 경우(9994)</h2>"
			+ "9994 오류 코드 발생 시 <strong>'/refresh'로 요청</strong>하여 리프레시토큰 재발급 받고 다시 요청하려던 엔드포인트로 재요청"
			+ "<h2>리프레시토큰이 만료된 경우(9993)</h2>"
			+ "로그아웃")
	@PostMapping("/sign-in")
	default void login(@RequestBody LoginRequest loginRequest) {
	}

	@Operation(summary = "사용자 정보 조회",
		description = "클라이언트에 보여질 사용자 정보")
	@PreAuthorize("hasRole('ROLE_USER')")
	BaseResponse<ManagerInformation> getProfile(
		@AuthenticationPrincipal CustomUserDetails userPrincipal
	);
}