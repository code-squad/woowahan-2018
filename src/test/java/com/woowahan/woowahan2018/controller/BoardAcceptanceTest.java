package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.BoardsDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

		BoardsDto response = template().getForObject("/api/boards/", BoardsDto.class);
		assertThat(response.getBoards(), hasItem(newBoard));
	}
}
