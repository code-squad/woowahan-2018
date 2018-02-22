package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/decks/{deckId}/cards")
public class CardController {

    @Autowired
    private DeckService deckService;

    @GetMapping("")
    public CommonResponse getCards(@PathVariable long deckId) throws DeckNotFoundException {
        List<Card> cards = deckService.findAllCards(deckId);
        return CommonResponse.success("Cards를 읽어왔습니다.", cards);
    }

    @PostMapping("")
    public CommonResponse postCard(@PathVariable long deckId,
                                   @RequestBody
                                   @Valid CardDto cardDto) throws DeckNotFoundException {
        Card card = deckService.createCard(deckId, cardDto);
        return CommonResponse.success("Card 생성", card);
    }
}
