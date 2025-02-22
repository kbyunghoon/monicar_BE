package org.controlcenter.common.security;

import java.util.Collection;
import java.util.Collections;

import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	private final String id;
	private final String userId;
	private final String password;
	private final Role role;
	private final String nickname;

	public CustomUserDetails(Manager manager) {
		this.id = manager.getId();
		this.userId = manager.getLoginId();
		this.password = manager.getPassword();
		this.role = manager.getRole();
		this.nickname = manager.getNickname();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(
			new SimpleGrantedAuthority(this.role.getRole())
		);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.getUserId();
	}
}
