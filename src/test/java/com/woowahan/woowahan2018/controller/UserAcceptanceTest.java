package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	private UserDto createUserDto() {
		return new UserDto("saram4030@gmail.com", "123456asdf!@", "이끼룩");
	}

	@Test
	public void create() throws Exception {
		UserDto newUser = createUserDto();

		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);
		assertThat(response, is(CommonResponse.success("성공적으로 가입했습니다.")));
	}

	@Test
	public void create_duplicate() throws Exception {
		UserDto newUser = createUserDto();

		template().postForObject("/api/users", newUser, CommonResponse.class);
		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);

		assertThat(response, is(CommonResponse.error("이미 가입된 사용자입니다.")));
	}

	@Test
	public void create_invalid_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram4030");

		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);
		assertThat(response, is(CommonResponse.error("잘못된 이메일 포맷입니다.")));
	}

	@Test
	public void create_invalid_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345abcdefg");

		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);
		assertThat(response, is(CommonResponse.error("잘못된 비밀번호 포맷입니다.")));
	}

	@Test
	public void create_short_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345");

		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);
		assertTrue(response.equals(CommonResponse.error("비밀번호는 10자 이상, 30자 이하이어야 합니다.")) ||
				response.equals(CommonResponse.error("잘못된 비밀번호 포맷입니다.")));
	}

}
