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


    @Test
    public void login_success() {
        UserDto testUserDto = new UserDto("javajigi@a.com", "12345asdfg!@", "자바지기");

        ResponseEntity<String> response = template().postForEntity("/api/users/login", testUserDto, String.class);
        log.debug("it's response : {}", response);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void login_fail() {
        UserDto testUserDto = new UserDto("javajigi@a.com", "12345asdfg!@!12", "자바지기");

        CommonResponse response = template().postForObject("/api/users/login", testUserDto, CommonResponse.class);

        assertThat(response, CoreMatchers.is(CommonResponse.error("아이디 또는 패스워드가 잘못되었습니다.")));
    }

//    @Test
//    public void logout() {
//        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/users/logout", null, String.class);
//
//        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
//    }
}
