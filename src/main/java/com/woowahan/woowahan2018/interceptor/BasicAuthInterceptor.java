package com.woowahan.woowahan2018.interceptor;

import com.woowahan.woowahan2018.domain.LoginUser;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.UnAuthenticationException;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(BasicAuthInterceptor.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authorization = request.getHeader("Authorization");
		log.debug("Authorization: {}", authorization);
		if (authorization == null || !authorization.startsWith("Basic")) {
			return true;
		}

		String base64Credentials = authorization.substring("Basic".length()).trim();
		String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
		final String[] values = credentials.split(":", 3);
		log.debug("email: {}", values[0]);
		log.debug("password: {}", values[1]);

		userService.login(values[0], values[1]);

		log.debug("Login Success: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return true;
	}
}