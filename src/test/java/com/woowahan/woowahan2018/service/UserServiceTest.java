package com.woowahan.woowahan2018.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void emailValidationTest() {
        assertTrue(userService.isValidEmail("saram4030@gmail.com"));
        assertTrue(userService.isValidEmail("saram4030@gmail.co.kr"));
        assertTrue(userService.isValidEmail("saram.4030@woowahan.com"));
    }

    @Test
    public void emailValidationTest_fail() {
        assertFalse(userService.isValidEmail("saram4030gmail.com"));
        assertFalse(userService.isValidEmail("saram4030gmail"));
        assertFalse(userService.isValidEmail("saram4030@gmail"));
    }

    @Test
    public void passwordValidationTest() {
        assertTrue(userService.isValidPassword("123456789!ab@123"));
        assertTrue(userService.isValidPassword("12345abc9!@123"));
        assertTrue(userService.isValidPassword("a123456789!@"));
        assertTrue(userService.isValidPassword("!123456789a!@"));
    }

    @Test
    public void passwordValidationTest_fail() {
        assertFalse(userService.isValidPassword("12345678910"));
        assertFalse(userService.isValidPassword("123456"));
        assertFalse(userService.isValidPassword("abcdefghij"));
        assertFalse(userService.isValidPassword("!1234567abc"));
        assertFalse(userService.isValidPassword("1234@567abc"));
        assertFalse(userService.isValidPassword("12345678abc%"));
    }
}
