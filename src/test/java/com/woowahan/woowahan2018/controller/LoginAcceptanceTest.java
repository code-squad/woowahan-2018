package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.AcceptanceTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);

    private UserDto createUserDto() {
        return new UserDto("javajigi@a.com", "12345asdfg!@", "자바지기");
    }

    @Test
    public void login_success() {
        UserDto existedUser = createUserDto();

        CommonResponse response = template().postForObject("/api/users/login", existedUser, CommonResponse.class);

        assertThat(response, is(CommonResponse.success(existedUser.getEmail())));
    }

    @Test
    public void login_fail() {
        UserDto existedUser = createUserDto().setPassword("12345asdfg!@!12");

        CommonResponse response = template().postForObject("/api/users/login", existedUser, CommonResponse.class);

        assertThat(response.getMessage(), is("PASSWORD.WRONG"));
    }
}
