package com.woowahan.woowahan2018.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class LoginUser implements UserDetails {
	private static final Logger log = LoggerFactory.getLogger(LoginUser.class);

	private String username;
	private String password;

	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	private Collection<GrantedAuthority> authorities;

	public LoginUser(String username, String password, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isEnabled = true;
		this.isCredentialsNonExpired = true;
	}

	public boolean isCorrectPassword(String inputPassword, PasswordEncoder encoder) {
		return encoder.matches(inputPassword, this.password);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginUser loginUser = (LoginUser) o;
		return Objects.equals(username, loginUser.username) &&
				Objects.equals(password, loginUser.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}

	@Override
	public String toString() {
		return "LoginUser{" +
				"username='" + username + '\'' +
				", encryptedPassword='" + password + '\'' +
				", isAccountNonExpired=" + isAccountNonExpired +
				", isAccountNonLocked=" + isAccountNonLocked +
				", isCredentialsNonExpired=" + isCredentialsNonExpired +
				", isEnabled=" + isEnabled +
				", authorities=" + authorities +
				'}';
	}
}
