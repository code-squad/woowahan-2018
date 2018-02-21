package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.group.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {
    @NotBlank(message = "이메일을 입력해주세요."
            , groups = First.class)
    @Size(min = 5, max = 30
            , message = "이메일은 5자 이상, 30자 이하이어야 합니다."
            , groups = Second.class)
    @Email(message = "이메일은 @를 포함해야 합니다."
            , groups = Third.class)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요."
            , groups = First.class)
    @Size(min = 10, max = 30, message = "비밀번호는 10자 이상, 30자 이하이어야 합니다."
            , groups = Second.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}"
            , message = "비밀번호는 문자/숫자를 각각 1개 이상, 특수문자를 2개 이상 포함해야 합니다."
            , groups = Third.class)
    private String password;

    @NotBlank(message = "사용자 이름을 입력해주세요."
            , groups = First.class)
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

    public String getName() {
        return name;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDto setName(String name) {
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
                ", username='" + name + '\'' +
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
