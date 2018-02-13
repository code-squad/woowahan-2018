package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class UserDto {

    @Email(message = "잘못된 이메일 포맷입니다.")
    @NotNull(message = "값을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@$!%*?&].*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{10,}"
            , message = "잘못된 비밀번호 포맷입니다.")
    @NotNull(message = "값을 입력해주세요.")
    private String password;

    @NotNull(message = "값을 입력해주세요.")
    private String username;

    public UserDto() {
    }

    public UserDto(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public User toUser(PasswordEncoder encoder) {
        return new User(email,
                encoder.encode(this.password),
                username);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }
}
