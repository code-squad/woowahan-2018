package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.DeckService;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/decks")
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DeckService deckService;

    @PostMapping("")
    public CommonResponse postDeck(@SignedInUser User signedInUser,
                                   @RequestBody
                                   @Valid DeckDto deckDto) throws BoardNotFoundException, UserNotFoundException {
        Deck deck = deckService.createDeck(signedInUser, deckDto);
        return CommonResponse.success("DECK.CREATE", deck);
    }
}
