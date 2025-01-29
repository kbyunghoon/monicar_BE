package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.presentation.dto.LoginRequest;
import org.controlcenter.company.presentation.swagger.ManagerApi;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagerController implements ManagerApi {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ManagerJpaRepository managerJpaRepository;
	private final RedisUtil redisUtil;
	private final JWTUtil jwtUtil;

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
}
