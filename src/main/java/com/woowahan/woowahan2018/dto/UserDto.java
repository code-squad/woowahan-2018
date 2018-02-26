package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.group.email.EmailFirstGroup;
import com.woowahan.woowahan2018.dto.group.email.EmailSecondGroup;
import com.woowahan.woowahan2018.dto.group.email.EmailThirdGroup;
import com.woowahan.woowahan2018.dto.group.name.NameFirstGroup;
import com.woowahan.woowahan2018.dto.group.password.PasswordFirstGroup;
import com.woowahan.woowahan2018.dto.group.password.PasswordSecondGroup;
import com.woowahan.woowahan2018.dto.group.password.PasswordThirdGroup;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {
    @NotBlank(message = "EMAIL.EMPTY"
            , groups = EmailFirstGroup.class)
    @Size(min = 5, max = 30
            , message = "EMAIL.LENGTH"
            , groups = EmailSecondGroup.class)
    @Email(message = "EMAIL.INVALID"
            , groups = EmailThirdGroup.class)
    private String email;

    @NotBlank(message = "PASSWORD.EMPTY"
            , groups = PasswordFirstGroup.class)
    @Size(min = 10, max = 30, message = "PASSWORD.LENGTH"
            , groups = PasswordSecondGroup.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@#^!%*?&].*[$@#^!%*?&])[A-Za-z\\d$@#^!%*?&]{10,}"
            , message = "PASSWORD.PATTERN"
            , groups = PasswordThirdGroup.class)
    private String password;

    @NotBlank(message = "NAME.EMPTY"
            , groups = NameFirstGroup.class)
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

    public String getName() {
        return name;
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

    public User toUser() {
        return new User(email, name);
    }

    public User toUser (PasswordEncoder encoder){
        return new User(email, encoder.encode(this.password) ,name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name);
    }

    @Override
    public int hashCode() {

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
