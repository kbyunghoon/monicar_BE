package org.controlcenter.company.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

import org.controlcenter.company.presentation.dto.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Builder
@Getter
public class Manager {
	private String id;
	private Long departmentId;
	private String email;
	private String loginId;
	private String password;
	private String nickname;
	private Role role;
	private LocalDateTime lastLoginAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static Manager toEntity(SignUpRequest signUpRequest, BCryptPasswordEncoder passwordEncoder) {
		return Manager.builder()
			.departmentId(1L)
			.email(signUpRequest.email())
			.loginId(signUpRequest.loginId())
			.password(passwordEncoder.encode(signUpRequest.password()))
			.nickname(signUpRequest.nickname())
			.role(Role.ROLE_USER)
			.build();
	}
}
