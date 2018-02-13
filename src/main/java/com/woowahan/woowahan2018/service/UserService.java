package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.domain.UserRepository;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.DuplicatedEmailException;
import com.woowahan.woowahan2018.exception.InvalidEmailFormatException;
import com.woowahan.woowahan2018.exception.InvalidPasswordFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void createUser(UserDto userDto) throws DuplicatedEmailException, InvalidEmailFormatException, InvalidPasswordFormatException {
		Optional<User> maybeUser = userRepository.findByEmail(userDto.getEmail());

		if (maybeUser.isPresent())
			throw new DuplicatedEmailException();

		if (!isValidEmail(userDto.getEmail()))
			throw new InvalidEmailFormatException();

		if (!isValidPassword(userDto.getPassword()))
			throw new InvalidPasswordFormatException();

		userRepository.save(userDto.toUser());
	}

	boolean isValidEmail(String email) {
		Pattern pwPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)* @[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pwPattern.matcher(email);

		return matcher.find();
	}

	boolean isValidPassword(String password) {
		Pattern pwPattern = Pattern.compile("^(?=.*\\d)(?=.*[A-Za-z])(?=.*[$@$!%*?&].*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{10,}");
		Matcher matcher = pwPattern.matcher(password);

		return matcher.find();
	}

	private Optional<User> findUserById(long id) {
		return Optional.ofNullable(userRepository.findOne(id));
	}

}
