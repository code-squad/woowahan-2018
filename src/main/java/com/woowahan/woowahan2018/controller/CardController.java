package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.exception.*;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.CardService;
import com.woowahan.woowahan2018.service.DeckService;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardService cardService;

    @GetMapping("/{cardId}")
    public CommonResponse getCard(@SignedInUser User signedInUser,
                                  @PathVariable long cardId) throws DeckNotFoundException, CardNotFoundException {
        Card card = cardService.findOneCardForMember(signedInUser, cardId);

        return CommonResponse.success("성공적으로 카드를 불러왔습니다.", card);
    }

    @PostMapping("")
    public CommonResponse postCard(@SignedInUser User signedInUser,
                                   @RequestBody
                                   @Valid CardDto cardDto) throws DeckNotFoundException {

        long deckId = cardDto.getDeckId();
        Card card = cardService.createCard(signedInUser, deckId, cardDto);

        Map<String, Object> content = new HashMap<>();
        content.put("card", card);
        content.put("deckId", deckId);

        return CommonResponse.success("Card 생성", content);
    }

    @PutMapping("/{cardId}/description")
    public CommonResponse postCardDescription(@SignedInUser User signedInUser,
                                              @PathVariable long cardId,
                                              @RequestBody Map<String, String> requestBody) throws CardNotFoundException, DeckNotFoundException, UnAuthenticationException {
        String description = requestBody.get("description");
        Card card = cardService.updateCardDescription(signedInUser, cardId, description);

        return CommonResponse.success("Description을 수정했습니다.", card);
    }
}
