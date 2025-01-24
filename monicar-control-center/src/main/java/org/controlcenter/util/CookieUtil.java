package org.controlcenter.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
	@Value("${jwt.expiration.access}")
	private Long accessExpiration;

	@Value("${jwt.expiration.refresh}")
	private Long refreshExpiration;

	public ResponseCookie createAccessTokenCookie(String value) {
		int maxAgeSec = (int)(accessExpiration / 1000);

		return ResponseCookie.from("access_token", value)
			.maxAge(maxAgeSec)
			.path("/")
			.httpOnly(true)
			.build();
	}

	public ResponseCookie createRefreshTokenCookie(String value) {
		int maxAgeSec = (int)(refreshExpiration / 1000);

		return ResponseCookie.from("refresh_token", value)
			.maxAge(maxAgeSec)
			.path("/")
			.httpOnly(true)
			.build();
	}
}
