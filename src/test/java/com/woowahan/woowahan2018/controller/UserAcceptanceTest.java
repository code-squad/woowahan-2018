package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import java.util.Arrays;
import java.util.List;

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
	public void create_null_email() throws Exception {
		UserDto newUser = createUserDto().setEmail(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일을 입력해주세요.")));
	}

	@Test
	public void create_empty_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일을 입력해주세요.")));
	}

	@Test
	public void create_short_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("boo");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일은 5자 이상, 30자 이하이어야 합니다.")));
	}

	@Test
	public void create_invalid_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram4030");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일은 @를 포함해야 합니다.")));
	}

	@Test
	public void create_null_password() throws Exception {
		UserDto newUser = createUserDto().setPassword(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호를 입력해주세요.")));
	}

	@Test
	public void create_empty_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호를 입력해주세요.")));
	}

	@Test
	public void create_short_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호는 10자 이상, 30자 이하이어야 합니다.")));
	}

	@Test
	public void create_invalid_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345abcdefg");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호는 문자/숫자를 각각 1개 이상, 특수문자를 2개 이상 포함해야 합니다.")));
	}

	@Test
	public void create_null_name() throws Exception {
		UserDto newUser = createUserDto().setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("name", "사용자 이름을 입력해주세요.")));
	}

	@Test
	public void create_empty_name() throws Exception {
		UserDto newUser = createUserDto().setName("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("name", "사용자 이름을 입력해주세요.")));
	}

	@Test
	public void create_empty_email_short_password() throws Exception {
		UserDto newUser = createUserDto().setEmail("").setPassword("12345");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(2));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일을 입력해주세요.")));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호는 10자 이상, 30자 이하이어야 합니다.")));
	}

	@Test
	public void create_invalid_email_null_name() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram").setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(2));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일은 @를 포함해야 합니다.")));
		assertTrue(responses.contains(CommonResponse.error("name", "사용자 이름을 입력해주세요.")));
	}

	@Test
	public void create_invalid_email_short_password_null_name() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram").setPassword("12345").setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(3));
		assertTrue(responses.contains(CommonResponse.error("email", "이메일은 @를 포함해야 합니다.")));
		assertTrue(responses.contains(CommonResponse.error("password", "비밀번호는 10자 이상, 30자 이하이어야 합니다.")));
		assertTrue(responses.contains(CommonResponse.error("name", "사용자 이름을 입력해주세요.")));
	}

}
