package org.controlcenter.config;

import java.util.List;

import org.controlcenter.common.security.CustomAuthenticationErrorHandler;
import org.controlcenter.common.security.CustomAuthenticationFailureHandler;
import org.controlcenter.common.security.CustomAuthenticationSuccessHandler;
import org.controlcenter.common.security.CustomUserDetailService;
import org.controlcenter.common.security.JWTFilter;
import org.controlcenter.common.security.LoginFilter;
import org.controlcenter.common.util.JWTTokenValidator;
import org.controlcenter.company.infrastructure.jpa.ManagerJpaRepository;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final JWTTokenValidator jwtTokenValidator;
	private final CorsProperties corsProperties;
	private final CustomAuthenticationErrorHandler errorHandler;
	private final CustomAuthenticationSuccessHandler successHandler;
	private final CustomAuthenticationFailureHandler failureHandler;
	private final AuthenticationConfiguration authenticationConfiguration;

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
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, ManagerJpaRepository managerJpaRepository) throws
		Exception {
		LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration));
		loginFilter.setAuthenticationSuccessHandler(successHandler);
		loginFilter.setAuthenticationFailureHandler(failureHandler);
		loginFilter.setFilterProcessesUrl("/api/v1/sign-in");

		http
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll()
			)
			.addFilterBefore(
				new JWTFilter(jwtTokenValidator, errorHandler,
					new CustomUserDetailService(managerJpaRepository)),
				LoginFilter.class)
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
