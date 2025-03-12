package org.controlcenter.config;

import java.util.List;

import org.controlcenter.common.security.CustomAuthenticationSuccessHandler;
import org.controlcenter.common.security.CustomUserDetailService;
import org.controlcenter.common.security.NoSessionAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	@Value("${security.secret-key}")
	String secretKey;

	@Value("${security.rememberme.cookieName}")
	private String rememberMeCookieName;

	@Value("${security.rememberme.cookieDomain:}")
	private String rememberMeCookieDomain;

	private final CorsProperties corsProperties;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final NoSessionAuthenticationFailureHandler noSessionAuthenticationFailureHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public static RoleHierarchy roleHierarchy() {
		return RoleHierarchyImpl.fromHierarchy("""
			        ROLE_ADMIN > ROLE_USER
			""");
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
		CustomUserDetailService customUserDetailService) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll()
			)
			.formLogin(form ->
				form.loginPage("/login")
					.loginProcessingUrl("/api/v1/sign-in")
					.usernameParameter("userId")
					.passwordParameter("password")
					.successHandler(customAuthenticationSuccessHandler)
					.failureHandler(noSessionAuthenticationFailureHandler)
			)
			.logout(logout ->
				logout.logoutUrl("/logout")
					.logoutSuccessUrl("/logoutOk")
			)
			.sessionManagement(auth ->
				auth.sessionFixation().changeSessionId())
			.rememberMe(rememberMe ->
				rememberMe
					.userDetailsService(customUserDetailService)
					.rememberMeCookieName(rememberMeCookieName)
					.rememberMeCookieDomain(rememberMeCookieDomain)
					.key(secretKey)
			)
			.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Cache-Control", "Content-Type", "X-API-KEY"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return new CorsFilter(source);
	}
}
