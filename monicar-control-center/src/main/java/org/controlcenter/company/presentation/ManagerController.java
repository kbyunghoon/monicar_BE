package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.security.CustomUserDetails;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.controlcenter.company.application.ManagerService;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.presentation.dto.LoginRequest;
import org.controlcenter.company.presentation.swagger.ManagerApi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagerController implements ManagerApi {
	private final RedisUtil redisUtil;
	private final JWTUtil jwtUtil;
	private final ManagerService managerService;

	@Override
	@PostMapping("/sign-in")
	public void login(@RequestBody LoginRequest loginRequest) {
	}

	@Override
	@PostMapping("/logout")
	public BaseResponse<Void> logout(
		@CookieValue("access-token") String accessToken,
		@CookieValue("refresh-token") String refreshToken
	) {
		String userId = jwtUtil.validateAndGetId(refreshToken);

		if (redisUtil.isRefreshTokenValid(userId) && !redisUtil.isAccessTokenBlacklisted(accessToken)) {
			redisUtil.saveAccessToken(accessToken, jwtUtil.getExpiredMs(accessToken));
			redisUtil.removeRefreshToken(userId);
		}

		return BaseResponse.success();
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('ROLE_USER')")
	public BaseResponse<ManagerInformation> getProfile(
		@AuthenticationPrincipal CustomUserDetails userPrincipal
	) {
		return BaseResponse.success(managerService.getProfile(userPrincipal.getId()));
	}
}
