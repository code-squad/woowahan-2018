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
		assertThat(response.getMessage(), is("USER.CREATE"));
	}

	@Test
	public void create_duplicate() throws Exception {
		UserDto newUser = createUserDto();

		template().postForObject("/api/users", newUser, CommonResponse.class);
		CommonResponse response = template().postForObject("/api/users", newUser, CommonResponse.class);

		assertThat(response.getMessage(), is("USER.ALREADY_EXISTS"));
	}

	@Test
	public void create_null_email() throws Exception {
		UserDto newUser = createUserDto().setEmail(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertThat(responses.get(0), is(CommonResponse.error("email", "EMAIL.EMPTY")));
	}

	@Test
	public void create_empty_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.EMPTY")));
	}

	@Test
	public void create_short_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("boo");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.LENGTH")));
	}

	@Test
	public void create_invalid_email() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram4030");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.PATTERN")));
	}

	@Test
	public void create_null_password() throws Exception {
		UserDto newUser = createUserDto().setPassword(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.EMPTY")));
	}

	@Test
	public void create_empty_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.EMPTY")));
	}

	@Test
	public void create_short_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.LENGTH")));
	}

	@Test
	public void create_invalid_password() throws Exception {
		UserDto newUser = createUserDto().setPassword("12345abcdefg");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.PATTERN")));
	}

	@Test
	public void create_null_name() throws Exception {
		UserDto newUser = createUserDto().setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("name", "NAME.EMPTY")));
	}

	@Test
	public void create_empty_name() throws Exception {
		UserDto newUser = createUserDto().setName("");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertTrue(responses.contains(CommonResponse.error("name", "NAME.EMPTY")));
	}

	@Test
	public void create_empty_email_short_password() throws Exception {
		UserDto newUser = createUserDto().setEmail("").setPassword("12345");

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(2));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.EMPTY")));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.LENGTH")));
	}

	@Test
	public void create_invalid_email_null_name() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram").setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(2));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.PATTERN")));
		assertTrue(responses.contains(CommonResponse.error("name", "NAME.EMPTY")));
	}

	@Test
	public void create_invalid_email_short_password_null_name() throws Exception {
		UserDto newUser = createUserDto().setEmail("saram").setPassword("12345").setName(null);

		List<CommonResponse> responses = Arrays.asList(template().postForObject("/api/users", newUser, CommonResponse[].class));

		assertThat(responses.size(), is(3));
		assertTrue(responses.contains(CommonResponse.error("email", "EMAIL.PATTERN")));
		assertTrue(responses.contains(CommonResponse.error("password", "PASSWORD.LENGTH")));
		assertTrue(responses.contains(CommonResponse.error("name", "NAME.EMPTY")));
	}

}
