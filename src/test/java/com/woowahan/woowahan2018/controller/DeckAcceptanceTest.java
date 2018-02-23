package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.AcceptanceTest;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeckAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(DeckAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        DeckDto newDeck = new DeckDto().setName("Test Deck");
        CommonResponse response = template().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);
        assertThat(response, is(CommonResponse.success("Deck 생성")));
    }

    @Test
    public void create_null() throws Exception {
        DeckDto newDeck = new DeckDto();
        CommonResponse response = template().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);
        assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));
    }

    @Test
    public void create_blank() throws Exception {
        DeckDto newDeck = new DeckDto().setName("");
        CommonResponse response = template().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);
        assertThat(response, is(CommonResponse.error("이름을 입력하세요.")));
    }

    @Test
    public void showAll() throws Exception {
        DeckDto newDeck = new DeckDto().setName("Test Deck");
        template().postForObject("/api/boards/1/decks", newDeck, CommonResponse.class);

        CommonResponse response = template().getForObject("/api/boards/1/decks", CommonResponse.class);
        assertThat(((List<LinkedHashMap<String, String>>)response.getContent()).get(0).containsValue("Test Deck"), is(true));
    }

}
