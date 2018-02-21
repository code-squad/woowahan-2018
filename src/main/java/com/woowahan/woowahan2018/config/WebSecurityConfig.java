package com.woowahan.woowahan2018.config;

import com.woowahan.woowahan2018.domain.LoginUser;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.security.RestAuthenticationFilter;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private UserService userService;

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Resource(name = "bcryptEncoder")
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomLogoutHandler customLogoutHandler = new CustomLogoutHandler();
		http
				.authorizeRequests()
				.antMatchers("/css/**",
						"/js/**",
						"/image/**",
						"/fonts/**",
						"/lib/**",
						"/",
						"/index.html",
						"/signup.html",
						"/api/users/**").permitAll()
				.antMatchers("/**").hasRole("LOGIN_USER")
				.anyRequest().authenticated();

		http
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/api/users/login")
				.permitAll();

		http
				.logout()
				.addLogoutHandler(customLogoutHandler)
				.logoutSuccessHandler(customLogoutHandler)
				.logoutUrl("/api/users/logout")
				.permitAll();

		http
				.csrf()
				.disable()
				.headers()
				.frameOptions()
				.disable();

		http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	private RestAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomLoginHandler customLoginHandler = new CustomLoginHandler();
		RestAuthenticationFilter filter = new RestAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(customLoginHandler);
		filter.setAuthenticationFailureHandler(customLoginHandler);
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl("/api/users/login");

		return filter;
	}

	private static void responseText(HttpServletResponse response, CommonResponse content) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		byte[] bytes = content.toJsonString().getBytes(StandardCharsets.UTF_8);
		response.setContentLength(bytes.length);
		response.getOutputStream().write(bytes);
		response.flushBuffer();
	}

	private class CustomLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
		// Login Success
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request,
		                                    HttpServletResponse response,
		                                    Authentication authentication) throws IOException {
			log.debug("User login successfully, name={}", authentication.getName());
			responseText(response, CommonResponse.success(authentication.getName()));
		}

		// Login Failure
		@Override
		public void onAuthenticationFailure(HttpServletRequest request,
		                                    HttpServletResponse response,
		                                    AuthenticationException exception) throws IOException {
			log.debug("User login failed, name={}", request.getUserPrincipal());
			responseText(response, CommonResponse.error("아이디 또는 비밀번호가 잘못되었습니다."));
		}
	}

	private class CustomLogoutHandler implements LogoutHandler, LogoutSuccessHandler {
		// Before Logout
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		}

		// After Logout
		@Override
		public void onLogoutSuccess(HttpServletRequest request,
		                            HttpServletResponse response,
		                            Authentication authentication) throws IOException {
			log.debug("authentication: {}", authentication);
			responseText(response, CommonResponse.success(authentication.getName() + " 로그아웃 되었습니다."));
		}
	}

	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
		githubFilter.setRestTemplate(githubTemplate);

		UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
		tokenServices.setRestTemplate(githubTemplate);

		githubFilter.setTokenServices(tokenServices);

		githubFilter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());

		return githubFilter;
	}

	private SimpleUrlAuthenticationSuccessHandler getAuthenticationSuccessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {
			public void onAuthenticationSuccess(HttpServletRequest request,
			                                    HttpServletResponse response,
			                                    Authentication authentication) throws IOException, ServletException {

				Map<String, String> map = (Map<String, String>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
				log.debug("authentication details: {}", map);

				LoginUser loginUser = (LoginUser) userService.loginWithGithub(map.get("email"), map.get("login"));

				this.setDefaultTargetUrl("/boards.html");
				super.onAuthenticationSuccess(request, response, authentication);
			}
		};
	}

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	public AuthorizationCodeResourceDetails github() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("security.oauth2.resource")
	public ResourceServerProperties githubResource() {
		return new ResourceServerProperties();
	}
}
