package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.User;

import java.util.Objects;

public class UserDto {
    private String username;
    private String email;
    private String password;

    public UserDto() {

    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
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

    public User toUser() {
        return new User(username, email, password);
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
