package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.security.CustomUserDetails;
import org.controlcenter.company.application.ManagerService;
import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.controlcenter.company.presentation.dto.LoginRequest;
import org.controlcenter.company.presentation.dto.SignUpRequest;
import org.controlcenter.company.presentation.swagger.ManagerApi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagerController implements ManagerApi {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ManagerService managerService;

	@PostMapping("/sign-up")
	public BaseResponse<Void> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
		managerService.signUp(Manager.toEntity(signUpRequest, bCryptPasswordEncoder));
		return BaseResponse.success();
	}

	@Override
	@PostMapping("/sign-in")
	public void login(@RequestBody LoginRequest loginRequest) {
	}

	@Override
	@PostMapping("/logout")
	public BaseResponse<Void> logout(
		@CookieValue(value = "access_token", required = false, defaultValue = "") String accessToken,
		@CookieValue(value = "refresh_token", required = false, defaultValue = "") String refreshToken,
		HttpServletResponse response
	) {
		managerService.logout(accessToken, refreshToken, response);

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
