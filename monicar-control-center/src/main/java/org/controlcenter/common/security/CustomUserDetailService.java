package org.controlcenter.common.security;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final ManagerJpaRepository managerJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws BusinessException {
		ManagerEntity managerEntity = managerJpaRepository.findByLoginId(loginId)
			.orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

		return new CustomUserDetails(managerEntity.toDomain());
	}
}
