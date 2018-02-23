package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.LoginUser;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.domain.UserRepository;
import com.woowahan.woowahan2018.dto.AccountType;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.DuplicatedEmailException;
import com.woowahan.woowahan2018.exception.UnAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource(name = "bcryptEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    private Optional<User> findUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    public void createUser(UserDto userDto) throws DuplicatedEmailException {
        Optional<User> maybeUser = userRepository.findByEmail(userDto.getEmail());

        if (maybeUser.isPresent())
            throw new DuplicatedEmailException("이미 가입된 사용자입니다.");

        userRepository.save(userDto.toUser(passwordEncoder));
    }

    public User login(UserDto userDto) throws UnAuthenticationException {
        return userRepository.findByEmail(userDto.getEmail())
                .filter(optionalUser -> optionalUser.isCorrectPassword(userDto.getPassword(), passwordEncoder))
                .orElseThrow(() -> new UnAuthenticationException("아이디 또는 패스워드가 잘못되었습니다."));
    }

    public void createGithubUser(UserDto userDto) throws DuplicatedEmailException {
        Optional<User> maybeUser = userRepository.findByEmail(userDto.getEmail());

        if (maybeUser.isPresent()) {
            throw new DuplicatedEmailException("이미 가입된 사용자입니다.");
        }

        userRepository.save(userDto.toUser());
    }

    public UserDetails githubLogin(String email, String username) {
        Optional<User> maybeUser = userRepository.findByEmail(email);
        if (!maybeUser.isPresent()) {
            User newUser = new User(email, username);
            return userRepository.save(newUser).toLoginUser(buildUserAuthority());
        }
        return maybeUser.get().toLoginUser(buildUserAuthority());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("로그인된 사용자가 없습니다."));
        LoginUser loginUser = user.toLoginUser(buildUserAuthority());

        log.debug("loginUser: {}", loginUser);
        return loginUser;
    }

    private List<GrantedAuthority> buildUserAuthority() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
        authorities.add(new SimpleGrantedAuthority("ROLE_LOGIN_USER"));
        return authorities;
    }
}
