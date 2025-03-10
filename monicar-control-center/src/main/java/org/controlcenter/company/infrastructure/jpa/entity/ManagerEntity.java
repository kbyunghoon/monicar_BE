package org.controlcenter.company.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.Role;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "manager")
@DynamicUpdate
public class ManagerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "manager_id")
	private String id;

	private Long departmentId;

	private String email;

	private String loginId;

	private String password;

	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private Role role;

	private LocalDateTime lastLoginAt;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static ManagerEntity from(Manager manager) {
		ManagerEntity managerEntity = new ManagerEntity();
		managerEntity.departmentId = manager.getDepartmentId();
		managerEntity.email = manager.getEmail();
		managerEntity.loginId = manager.getLoginId();
		managerEntity.password = manager.getPassword();
		managerEntity.nickname = manager.getNickname();
		managerEntity.role = manager.getRole();
		managerEntity.lastLoginAt = manager.getLastLoginAt();
		managerEntity.createdAt = manager.getCreatedAt();
		managerEntity.updatedAt = manager.getUpdatedAt();
		managerEntity.deletedAt = manager.getDeletedAt();
		return managerEntity;
	}

	public Manager toDomain() {
		return Manager.builder()
			.id(id)
			.departmentId(departmentId)
			.email(email)
			.loginId(loginId)
			.password(password)
			.nickname(nickname)
			.role(role)
			.lastLoginAt(lastLoginAt)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}
}
