package org.controlcenter.common.security;

import lombok.Getter;

@Getter
public class LoginRequest {
	String userId;
	String password;
}
