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

    @NotNull(message = "값을 입력해주세요."
            , groups = { TrelloUserGroup.class, GithubUserGroup.class })
    private String name;

    private AccountType accountType;

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

    public String getname() {
        return name;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDto setname(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public UserDto setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public User toUser() {
        return new User(email, name, accountType);
    }

    public User toUser(PasswordEncoder encoder) {
        return new User(email,
                encoder.encode(password),
                name,
                accountType);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", accountType=" + accountType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name) &&
                accountType == userDto.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, accountType);
    }
}
