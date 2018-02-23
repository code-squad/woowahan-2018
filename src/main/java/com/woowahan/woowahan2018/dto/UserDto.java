package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.group.GithubUserGroup;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.woowahan.woowahan2018.dto.group.TrelloUserGroup;

import javax.validation.GroupSequence;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {
    @Email(message = "잘못된 이메일 포맷입니다."
            , groups = { TrelloUserGroup.class, GithubUserGroup.class })
    @NotNull(message = "값을 입력해주세요."
            , groups = { TrelloUserGroup.class, GithubUserGroup.class })
    @Size(min = 5, max = 30
            , message = "이메일은 5자 이상, 30자 이하이어야 합니다."
            , groups = { TrelloUserGroup.class, GithubUserGroup.class })
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}"
            , message = "잘못된 비밀번호 포맷입니다."
            , groups = TrelloUserGroup.class)
    @NotNull(message = "값을 입력해주세요."
            , groups = TrelloUserGroup.class)
    @Size(min = 10, max = 30, message = "비밀번호는 10자 이상, 30자 이하이어야 합니다."
            , groups = TrelloUserGroup.class)
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

    public User toUser() {
        return new User(email, name);
    }

    public User toUser (PasswordEncoder encoder){
        return new User(email,
                encoder.encode(this.password),
                name);
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name);
    }

    @Override
    public int hashCode () {

        return Objects.hash(email, password, name);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
