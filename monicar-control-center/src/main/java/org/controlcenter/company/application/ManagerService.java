package org.controlcenter.company.application;

import java.time.LocalDateTime;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final ManagerJpaRepository managerJpaRepository;

	public ManagerInformation getProfile(String id) {
		return managerRepository.getUserProfile(id);
	}

	@Transactional
	public void updateLastLoginAt(String loginId) {
		ManagerEntity managerEntity = managerJpaRepository.findByLoginId(loginId)
			.orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

		managerEntity.setLastLoginAt(LocalDateTime.now());
	}
}
