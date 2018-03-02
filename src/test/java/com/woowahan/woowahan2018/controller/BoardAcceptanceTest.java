package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.ResponseStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoardAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(BoardAcceptanceTest.class);

	@Test
	public void create() throws Exception {
		BoardDto newBoard = new BoardDto("Test Board");
		CommonResponse response = basicAuthTemplate().postForObject("/api/boards/", newBoard, CommonResponse.class);

		assertThat(response.getStatus(), is(ResponseStatus.OK));
		assertThat(response.getMessage(), is("BOARD.CREATE"));
	}

	@Test
	public void create_null() throws Exception {
		BoardDto newBoard = new BoardDto();
		List<CommonResponse> responses = Arrays.asList(basicAuthTemplate().postForObject("/api/boards/", newBoard, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertThat(responses.get(0).getStatus(), is(ResponseStatus.FAIL));
		assertThat(responses.get(0).getMessage(), is("TEXT.EMPTY"));
	}

	@Test
	public void create_blank() throws Exception {
		BoardDto newBoard = new BoardDto("");
		List<CommonResponse> responses = Arrays.asList(basicAuthTemplate().postForObject("/api/boards/", newBoard, CommonResponse[].class));

		assertThat(responses.size(), is(1));
		assertThat(responses.get(0).getStatus(), is(ResponseStatus.FAIL));
		assertThat(responses.get(0).getMessage(), is("TEXT.EMPTY"));
	}

	@Test
	public void showAll() {
		BoardDto newBoard = new BoardDto("Test Board!!");
		basicAuthTemplate().postForObject("/api/boards/", newBoard, CommonResponse.class);

		CommonResponse response = basicAuthTemplate().getForObject("/api/boards/", CommonResponse.class);

		log.debug("response: {}", response);
		assertThat(response.getContent().toString(), containsString(newBoard.getName()));
	}

}
