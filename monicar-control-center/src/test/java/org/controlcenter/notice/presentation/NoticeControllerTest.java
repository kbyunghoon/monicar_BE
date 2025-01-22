package org.controlcenter.notice.presentation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.controlcenter.notice.application.NoticeRepository;
import org.controlcenter.notice.domain.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("NoticeController mock 테스트")
@WebMvcTest(NoticeController.class)
class NoticeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	NoticeRepository repository;

	@TestConfiguration
	public static class SecurityConfiguration {

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authz) -> authz
					.anyRequest().permitAll()
				);
			return http.build();
		}

	}

	@DisplayName("notice 개수가 0개일 때 entity not found 에러 응답")
	@Test
	void getAllNotice() throws Exception {
		List<Notice> emptyList = Collections.emptyList();
		when(repository.findAll()).thenReturn(emptyList);

		var result = mockMvc.perform(get("/v1/control-center/notices")
			.accept(MediaType.APPLICATION_JSON));

		result.andDo(print());
		result.andExpect(jsonPath("$.errorCode").value(1003));
	}
}