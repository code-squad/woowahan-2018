package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {

    @Email(message = "잘못된 이메일 포맷입니다.")
    @NotNull(message = "값을 입력해주세요.")
    @Size(min = 5, max = 30, message = "이메일은 5자 이상, 30자 이하이어야 합니다.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@$!%*?&].*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{10,}"
            , message = "잘못된 비밀번호 포맷입니다.")
    @NotNull(message = "값을 입력해주세요.")
    @Size(min = 10, max = 30, message = "비밀번호는 10자 이상, 30자 이하이어야 합니다.")
    private String password;

    @NotNull(message = "값을 입력해주세요.")
    private String name;

    public UserDto() {
    }

    public UserDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public User toUser(PasswordEncoder encoder) {
        return new User(email,
                encoder.encode(this.password),
                name);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(name, userDto.name) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}
