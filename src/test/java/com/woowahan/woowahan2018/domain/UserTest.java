package com.woowahan.woowahan2018.domain;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserTest {

    @Test
    public void matchPasswordTest() {
        User user = new User("sehwan@woowahan.com",
                "$2a$10$loY.d0SmKWZw6T3XQKi92u5nCtrIhUMKr4n0V9ma3MtnczLFzCM5y",
                "sehwan");
        boolean result = user.isCorrectPassword("12345asdfg!@", new BCryptPasswordEncoder());

        assertTrue(result);
    }

    @Test
    public void matchPasswordTest_fail() {
        User user = new User("sehwan@woowahan.com",
                "$2a$10$loY.d0SmKWZw6T3XQKi92u5nCtrIhUMKr4n0V9ma3MtnczLFzCM5y",
                "sehwan");
        boolean result = user.isCorrectPassword("123asdfg!@", new BCryptPasswordEncoder());

        assertFalse(result);
    }
}
