package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user",
    indexes = {@Index(name = "my_index_name",  columnList="email", unique = true)})
public class User extends AbstractEntity {
    @Column(name="email", nullable = false)
    @Length(min = 5, max = 30)
    private String email;

    @Column
    private String encryptedPassword;

    @Column(nullable = false)
    @Length(max = 30)
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "members")
    private List<Board> boards = new ArrayList<>();

    public User() {

    }

    public User(String email, String encryptedPassword, String name) {
        this(email, name);
        this.encryptedPassword = encryptedPassword;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public boolean isCorrectPassword(String inputPassword, PasswordEncoder encoder) {
        return encoder.matches(inputPassword, this.encryptedPassword);
    }

    public List<Board> getBoards() {
        return Collections.unmodifiableList(boards);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LoginUser toLoginUser(List<GrantedAuthority> authorities) {
        return new LoginUser(email, encryptedPassword, authorities);
    }

    public boolean matchId(long id) {
        return getId() == id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(encryptedPassword, user.encryptedPassword) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, encryptedPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
