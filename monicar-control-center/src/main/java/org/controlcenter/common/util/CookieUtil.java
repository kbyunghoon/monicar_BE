package org.controlcenter.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
	@Value("${cookie.domain}")
	private String cookieDomain;

	public ResponseCookie createAccessTokenCookie(String value, Long expirationMillis) {
		int maxAgeSec = (int)(expirationMillis / 1000);

		return ResponseCookie.from("access_token", value)
			.maxAge(maxAgeSec)
			.path("/")
			.secure(true)
			.sameSite("None")
			.domain(cookieDomain)
			.httpOnly(true)
			.build();
	}

	public ResponseCookie createRefreshTokenCookie(String value, Long expirationMillis) {
		int maxAgeSec = (int)(expirationMillis / 1000);

		return ResponseCookie.from("refresh_token", value)
			.maxAge(maxAgeSec)
			.path("/")
			.secure(true)
			.sameSite("None")
			.domain(cookieDomain)
			.httpOnly(true)
			.build();
	}
}
