package org.controlcenter.company.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
	@NotBlank(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "유효하지 않은 이메일 형식입니다.")
	String email,

	@NotBlank(message = "아이디를 입력해주세요.")

	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영문자와 숫자만 사용할 수 있습니다.")
	String loginId,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password,

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z가-힣0-9._-]{2,10}$", message = "닉네임은 2~10자 이내로 설정해야 하며, 특수문자는 ., _, -만 사용할 수 있습니다.")
	String nickname
) {
}
