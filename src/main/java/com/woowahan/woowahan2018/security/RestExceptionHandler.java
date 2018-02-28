package com.woowahan.woowahan2018.security;

import com.woowahan.woowahan2018.dto.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<CommonResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<CommonResponse> responses = result.getFieldErrors()
				.stream()
				.map(fieldError -> CommonResponse.error(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		log.debug("Error responses: {}", responses);
		return responses;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Throwable.class)
	public CommonResponse handleControllerException(HttpServletRequest req, Throwable ex) {
		log.debug("Exception raised. {}", ex.getMessage());
		return CommonResponse.error(ex.getMessage());
	}
}