package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.DeckService;
import com.woowahan.woowahan2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards/{boardId}/decks/{deckId}/cards")
public class CardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private DeckService deckService;

    @PostMapping("")
    public CommonResponse postCard(Principal principal,
                                   @PathVariable long boardId,
                                   @PathVariable long deckId,
                                   @RequestBody
                                   @Valid CardDto cardDto) throws DeckNotFoundException, UserNotFoundException, BoardNotFoundException {

        User signedInUser = userService.findUserByEmail(principal.getName());
        boardService.findOneBoardForMember(signedInUser, boardId);
        Card card = deckService.createCard(deckId, boardId, cardDto);

        Map<String, Object> content = new HashMap<>();
        content.put("card", card);
        content.put("deckId", deckId);

        return CommonResponse.success("Card 생성", content);
    }
}
