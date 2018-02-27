package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoardAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(BoardAcceptanceTest.class);

	@Test
	public void create() throws Exception {
		BoardDto newBoard = new BoardDto("Test Board");
		CommonResponse response = template().postForObject("/api/boards/", newBoard, CommonResponse.class);
		assertThat(response, is(CommonResponse.success("Board를 성공적으로 생성했습니다.")));
	}

	@Test
	public void create_null() throws Exception{
		BoardDto newBoard = new BoardDto();
		CommonResponse response = template().postForObject("/api/boards/", newBoard, CommonResponse.class);
		assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));
	}

	@Test
	public void create_blank() throws Exception{
		BoardDto newBoard = new BoardDto("");
		CommonResponse response = template().postForObject("/api/boards/", newBoard, CommonResponse.class);
		assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));
	}

	@Test
	public void showAll() {
        BoardDto newBoard = new BoardDto("Test Board!!");
        template().postForObject("/api/boards/", newBoard, CommonResponse.class);

		CommonResponse response = template().getForObject("/api/boards/", CommonResponse.class);
		log.debug("response : {} ", response);
		LinkedHashMap<String, List<LinkedHashMap<String, String>>> content
				= (LinkedHashMap<String, List<LinkedHashMap<String, String>>>) response.getContent();
		List<LinkedHashMap<String, String>> boards = content.get("boards");
		assertThat(boards.get(1).containsValue("Test Board!!"), is(true));
	}

}
