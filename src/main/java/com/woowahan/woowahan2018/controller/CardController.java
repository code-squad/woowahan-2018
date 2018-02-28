package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.MovedCardDto;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.exception.UnAuthenticationException;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.CardService;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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

		return CommonResponse.success("CARD.READ_SINGLE", card);
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

		return CommonResponse.success("CARD.CREATE", content);
	}

	@PutMapping("/{cardId}/description")
	public CommonResponse postCardDescription(@SignedInUser User signedInUser,
	                                          @PathVariable long cardId,
	                                          @RequestBody Map<String, String> requestBody) throws CardNotFoundException, DeckNotFoundException, UnAuthenticationException {
		String description = requestBody.get("description");
		Card card = cardService.updateCardDescription(signedInUser, cardId, description);

		return CommonResponse.success("CARD.UPDATE_DESCRIPTION", card);
	}

	@PutMapping("/{cardId}/move")
	public CommonResponse moveCard(@SignedInUser User signedInUser,
	                               @PathVariable long cardId,
	                               @RequestBody MovedCardDto movedCardDto) throws CardNotFoundException, DeckNotFoundException, UnAuthenticationException {

		Card card = cardService.moveCard(signedInUser, cardId, movedCardDto.getDeckId(),
				movedCardDto.getStandardCardId(), movedCardDto.getStandardType());

		return CommonResponse.success("CARD.MOVE", card);
	}
}
