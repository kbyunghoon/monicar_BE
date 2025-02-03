package org.controlcenter.common.security;

import java.util.regex.Pattern;

import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private static final String REGEX_USER_ID = "^[A-Za-z0-9]+$";

	private final ManagerJpaRepository managerJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws AuthenticationException {
		if (!Pattern.matches(REGEX_USER_ID, loginId)) {
			throw new BadCredentialsException(ErrorCode.INVALID_TYPE_USERID_VALUE.getMessage());
		}

		ManagerEntity managerEntity = managerJpaRepository.findByLoginId(loginId)
			.orElseThrow(() -> new BadCredentialsException(ErrorCode.LOGIN_FAILED.getMessage()));

		return new CustomUserDetails(managerEntity.toDomain());
	}
}
