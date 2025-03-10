package org.controlcenter.company.application;

import java.time.LocalDateTime;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.util.CookieUtil;
import org.controlcenter.common.util.JWTUtil;
import org.controlcenter.common.util.RedisUtil;
import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Repository
@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final ManagerJpaRepository managerJpaRepository;
	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final CookieUtil cookieUtil;

	@Transactional
	public void signUp(Manager manager) {
		managerJpaRepository.findByLoginId(manager.getLoginId())
			.ifPresent(existUser -> {
				throw new BusinessException(ErrorCode.USER_ID_ALREADY_EXIST);
			});
		managerJpaRepository.findByEmail(manager.getEmail())
			.ifPresent(existUser -> {
				throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXIST);
			});
		managerJpaRepository.save(ManagerEntity.from(manager));
	}

	public ManagerInformation getProfile(String id) {
		return managerRepository.getUserProfile(id);
	}

	@Transactional
	public void updateLastLoginAt(String loginId) {
		ManagerEntity managerEntity = managerJpaRepository.findByLoginId(loginId)
			.orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

		managerEntity.setLastLoginAt(LocalDateTime.now());
	}

	public void logout(String accessToken, String refreshToken, HttpServletResponse response) {
		if (accessToken.isEmpty() && refreshToken.isEmpty()) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		if (!accessToken.isEmpty() && jwtUtil.isValidTokenIgnoreExpiration(accessToken)) {
			redisUtil.saveAccessToken(accessToken, jwtUtil.getExpiredMs(accessToken));
		}

		if (!refreshToken.isEmpty() && jwtUtil.isValidTokenIgnoreExpiration(refreshToken)) {
			String userId = jwtUtil.validateAndGetId(refreshToken);
			redisUtil.removeRefreshToken(userId);
		}

		response.addHeader("Set-Cookie", cookieUtil.createAccessTokenCookie("", 0L).toString());
		response.addHeader("Set-Cookie",
			cookieUtil.createRefreshTokenCookie("", 0L).toString());
	}
}
