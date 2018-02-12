package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.DuplicatedEmailException;
import com.woowahan.woowahan2018.exception.InvalidPasswordFormatException;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("")
    public CommonResponse createUser(UserDto userDto) {
        try {
            userService.createUser(userDto);
            return CommonResponse.success("Created user.");
        } catch(DuplicatedEmailException e) {
            log.debug(e.getMessage());
            return CommonResponse.error("Duplicated email.");
        } catch(InvalidPasswordFormatException e) {
            log.debug(e.getMessage());
            return CommonResponse.error("Wrong password format.");
        }
    }
}
