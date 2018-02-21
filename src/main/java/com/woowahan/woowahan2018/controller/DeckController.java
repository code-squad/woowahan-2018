package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/decks")
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping("")
    public CommonResponse getDecks(@PathVariable long boardId) throws BoardNotFoundException {
        List<Deck> decks = boardService.findAllDecks(boardId);
        return CommonResponse.success("Decks을 읽어왔습니다.", decks);
    }

    @PostMapping("")
    public CommonResponse postDeck(@PathVariable long boardId,
                                   @RequestBody
                                   @Valid DeckDto deckDto) throws BoardNotFoundException {
        log.debug("hello {}", deckDto);
        Deck deck = boardService.createDeck(boardId, deckDto);
        return CommonResponse.success("Deck 생성", deck);
    }

    @DeleteMapping("/{deckId}")
    public CommonResponse deleteDeck(@PathVariable long boardId,
                                     @PathVariable long deckId) throws BoardNotFoundException {
        boardService.deleteDeck(boardId, deckId);
        return CommonResponse.success("Deck 삭제");
    }
}
