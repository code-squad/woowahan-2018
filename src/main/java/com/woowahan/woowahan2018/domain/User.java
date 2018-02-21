package com.woowahan.woowahan2018.domain;

import com.woowahan.woowahan2018.dto.AccountType;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, unique = true)
    @Length(min = 5, max = 30)
    private String email;

    @Column
    private String encryptedPassword;

    @Column(nullable = false)
    @Length(max = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public User() {

    }

    public User(String email, String encryptedPassword, String name) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.name = name;
    }

    public User(String email, String encryptedPassword, String name, AccountType accountType) {
        this(email, name, accountType);
        this.encryptedPassword = encryptedPassword;
    }

    public User(String email, String name, AccountType accountType) {
        this.email = email;
        this.name = name;
        this.accountType = accountType;
    }

    public boolean isCorrectPassword(String inputPassword, PasswordEncoder encoder) {
        return encoder.matches(inputPassword, this.encryptedPassword);
    }

    public String getEmail() {
        return email;
    }

    public String getname() {
        return name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public LoginUser toLoginUser(List<GrantedAuthority> authorities) {
        return new LoginUser(email, encryptedPassword, authorities);
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", name='" + name + '\'' +
                ", accountType=" + accountType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(encryptedPassword, user.encryptedPassword) &&
                Objects.equals(name, user.name) &&
                accountType == user.accountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, encryptedPassword, name, accountType);
    }
}
