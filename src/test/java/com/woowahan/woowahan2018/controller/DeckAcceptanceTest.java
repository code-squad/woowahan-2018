package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.dto.ResponseStatus;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import support.AcceptanceTest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeckAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(DeckAcceptanceTest.class);

    @Before
    public void setUp() throws Exception {
        BoardDto newBoard = new BoardDto("Test Board!!");
        basicAuthTemplate().postForObject("/api/boards/", newBoard, CommonResponse.class);
    }

    @Test
    public void create() throws Exception {
        DeckDto newDeck = new DeckDto().setName("Test Deck").setBoardId(1);
        CommonResponse response = basicAuthTemplate().postForObject("/api/decks", newDeck, CommonResponse.class);

        assertThat(response.getStatus(), is(ResponseStatus.OK));
        assertThat(response.getMessage(), is("DECK.CREATE"));
    }

    @Test
    public void create_null() throws Exception {
        DeckDto newDeck = new DeckDto().setBoardId(1);
        List<CommonResponse> response = Arrays.asList(basicAuthTemplate().postForObject("/api/decks", newDeck, CommonResponse[].class));

        assertThat(response.get(0).getStatus(), is(ResponseStatus.FAIL));
        assertThat(response.get(0).getMessage(), is("TITLE.EMPTY"));
    }

    @Test
    public void create_blank() throws Exception {
        DeckDto newDeck = new DeckDto().setName("").setBoardId(1);
        List<CommonResponse> response = Arrays.asList(basicAuthTemplate().postForObject("/api/decks", newDeck, CommonResponse[].class));

        assertThat(response.get(0).getStatus(), is(ResponseStatus.FAIL));
        assertThat(response.get(0).getMessage(), is("TITLE.EMPTY"));

    }

    @Test
    public void showAll() throws Exception {
        DeckDto newDeck = new DeckDto().setName("Test Deck").setBoardId(1);
        basicAuthTemplate().postForObject("/api/decks", newDeck, CommonResponse.class);

        CommonResponse response = basicAuthTemplate().getForObject("/api/boards", CommonResponse.class);
        assertThat(response.getContent().toString(), containsString(newDeck.getName()));
    }

}
