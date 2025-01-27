package org.controlcenter.company.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

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
}
