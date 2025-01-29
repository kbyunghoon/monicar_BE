package org.controlcenter.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey secretKey;
	private static final String ALGORITHM = "HS512";
	private static final String JWT_TYPE = "JWT";

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String validateAndGetId(String token) throws JwtException {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("user_id", String.class);
	}

	public String createJwt(String userId, Long expiredMs, String subject) {
		return Jwts.builder()
			.claim("user_id", userId)
			.setHeader(createHeader())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
			.setSubject(subject)
			.signWith(secretKey)
			.compact();
	}

	public boolean isValidToken(String token) {
		try {
			// 만료 여부 상관없이 토큰의 유효성을 검증
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	public boolean isExpiredStrict(String token) throws JwtException {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getExpiration()
			.before(new Date());
	}

	private static Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("alg", ALGORITHM);
		header.put("typ", JWT_TYPE);
		return header;
	}

	public Long getExpiredMs(String token) {
		Claims tokenClaims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return tokenClaims.getExpiration().getTime() - tokenClaims.getIssuedAt().getTime();
	}
}
