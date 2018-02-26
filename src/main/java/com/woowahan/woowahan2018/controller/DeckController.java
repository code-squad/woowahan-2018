package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/decks")
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @PostMapping("")
    public CommonResponse postDeck(Principal principal,
                                   @PathVariable long boardId,
                                   @RequestBody
                                   @Valid DeckDto deckDto) throws BoardNotFoundException, UserNotFoundException {
        User signedInUser = userService.findUserByEmail(principal.getName());
        boardService.findOneBoardForMember(signedInUser, boardId);
        Deck deck = boardService.createDeck(boardId, deckDto);
        return CommonResponse.success("Deck 생성", deck);
    }
}
