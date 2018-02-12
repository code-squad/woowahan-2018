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
    public void passwordValidationTest() {
        boolean result = userService.isValidPassword("123456789!@");
        assertTrue(result);
    }

    @Test
    public void passwordValidationTest_fail() {
        boolean result = userService.isValidPassword("123456789!");
        assertFalse(result);
    }
}
