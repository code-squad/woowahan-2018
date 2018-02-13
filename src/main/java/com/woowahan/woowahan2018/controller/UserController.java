package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("")
    public CommonResponse createUser(@RequestBody @Valid UserDto userDto) throws MethodArgumentNotValidException {
        log.debug("userDto: {}", userDto);
        userService.createUser(userDto);
        return CommonResponse.success("성공적으로 가입했습니다.");
    }
}
