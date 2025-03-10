package org.controlcenter.company.application;

import java.time.LocalDateTime;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final ManagerJpaRepository managerJpaRepository;

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
}
