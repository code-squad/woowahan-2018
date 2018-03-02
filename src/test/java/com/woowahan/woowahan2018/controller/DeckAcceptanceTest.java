package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.dto.ResponseStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import support.AcceptanceTest;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeckAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(DeckAcceptanceTest.class);

    private void createBoard(TestRestTemplate template) {
        BoardDto newBoard = new BoardDto("Test Board!!");
        template.postForObject("/api/boards/", newBoard, CommonResponse.class);
    }

    @Test
    public void create() throws Exception {
        createBoard(basicAuthTemplate());

        DeckDto newDeck = new DeckDto().setName("Test Deck").setBoardId(1);
        CommonResponse response = basicAuthTemplate().postForObject("/api/decks", newDeck, CommonResponse.class);

        assertThat(response.getStatus(), is(ResponseStatus.OK));
        assertThat(response.getMessage(), is("DECK.CREATE"));
    }

    @Test
    public void create_null() throws Exception {
        DeckDto newDeck = new DeckDto();
        CommonResponse response = basicAuthTemplate().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);

        assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));
    }

    @Test
    public void create_blank() throws Exception {
        DeckDto newDeck = new DeckDto().setName("");
        CommonResponse response = basicAuthTemplate().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);
        assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));

    }

    @Test
    public void showAll() throws Exception {
        DeckDto newDeck = new DeckDto().setName("Test Deck");
        template().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);

        CommonResponse response = basicAuthTemplate().getForObject("/api/boards/1/decks", CommonResponse.class);

        assertThat(((List<LinkedHashMap<String, String>>)response.getContent()).get(0).containsValue("Test Deck"), is(true));
    }

}
