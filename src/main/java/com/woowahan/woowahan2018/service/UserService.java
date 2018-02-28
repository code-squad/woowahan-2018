package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.LoginUser;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.domain.UserRepository;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.DuplicatedEmailException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;
import com.woowahan.woowahan2018.exception.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public void createUser(UserDto userDto) throws DuplicatedEmailException {
		Optional<User> maybeUser = userRepository.findByEmail(userDto.getEmail());

		if (maybeUser.isPresent())
			throw new DuplicatedEmailException();

		userRepository.save(userDto.toUser(passwordEncoder));
	}

	public UserDetails login(String email, String password) throws WrongPasswordException {
		User user = userRepository.findByEmail(email).orElseThrow(UnAuthorizedException::new);
		if (!user.isCorrectPassword(password, passwordEncoder))
			throw new WrongPasswordException();

		return registerAuthentication(user);
	}

	public UserDetails loginWithGithub(String email, String username) {
		Optional<User> maybeUser = userRepository.findByEmail(email);
		if (!maybeUser.isPresent()) {
			User user = userRepository.save(new User(email, username));
			return registerAuthentication(user);
		}

		return registerAuthentication(maybeUser.get());
	}

    public UserDetails githubLogin(String email, String username) {
        Optional<User> maybeUser = userRepository.findByEmail(email);
        if (!maybeUser.isPresent()) {
            User newUser = new User(email, username);
            return userRepository.save(newUser).toLoginUser(buildUserAuthority());
        }
        return maybeUser.get().toLoginUser(buildUserAuthority());
    }

	public List<Board> getBoardList(String email) throws UserNotFoundException {
		User user = findUserByEmail(email);
		return user.getBoards();
	}

	public User findUserByEmail(String email) throws UserNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(UserNotFoundException::new);
	}

	private LoginUser registerAuthentication(User user) {
		LoginUser loginUser = user.toLoginUser(buildUserAuthority());
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
				loginUser, null, loginUser.getAuthorities()
		));

		return loginUser;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("USER.NO_SINGED_IN_USER"));
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
