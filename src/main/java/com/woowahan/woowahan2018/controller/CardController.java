package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.MovedCardDto;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.dto.group.NamePriorityGroup;
import com.woowahan.woowahan2018.exception.*;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class CardController {
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
	                               @Validated(value = {NamePriorityGroup.class})
	                               @RequestBody CardDto cardDto) throws DeckNotFoundException {

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
                                              @RequestBody CardDto cardDto) throws CardNotFoundException, DeckNotFoundException, UnAuthenticationException {
        Card card = cardService.updateCardDescription(signedInUser, cardId, cardDto);

        return CommonResponse.success("CARD.UPDATE_DESCRIPTION", card);
    }

    @PutMapping("/{cardId}/date")
    public CommonResponse postCardDueDate(@SignedInUser User signedInUser,
                                          @PathVariable long cardId,
                                          @RequestBody CardDto cardDto) throws CardNotFoundException, DeckNotFoundException {
        Card card = cardService.updateCardDate(signedInUser, cardId, cardDto);

        return CommonResponse.success("Due Date를 수정했습니다.", card);
    }

    @PostMapping("/{cardId}/assignees")
    public CommonResponse addMember(@SignedInUser User signedInUser,
                                    @PathVariable long cardId,
                                    @RequestBody UserDto assignee) throws UserNotFoundException, CardNotFoundException, DeckNotFoundException, ExistMemberException {
        Card card = cardService.addAssignee(signedInUser, cardId, assignee.getEmail());

        return CommonResponse.success("Assignee를 성공적으로 추가했습니다.", card.getAssignees());
    }

    @DeleteMapping("/{cardId}/assignees")
    public CommonResponse deleteMember(@SignedInUser User signedInUser,
                                    @PathVariable long cardId,
                                    @RequestBody UserDto assignee) throws UserNotFoundException, CardNotFoundException, DeckNotFoundException {
        Card card = cardService.deleteAssignee(signedInUser, cardId, assignee.getEmail());

        return CommonResponse.success("Assignee를 성공적으로 삭제했습니다.", card.getAssignees());
    }

    @GetMapping("/{cardId}/assignees")
    public CommonResponse getMember(@SignedInUser User signedInUser,
                                    @PathVariable long cardId) throws CardNotFoundException, DeckNotFoundException {
        Map<String, List<User>> result = cardService.getAssignees(signedInUser, cardId);

        return CommonResponse.success("Assignee를 성공적으로 불러왔습니다", result);
    }

	@PutMapping("/{cardId}/move")
	public CommonResponse moveCard(@SignedInUser User signedInUser,
	                               @PathVariable long cardId,
	                               @RequestBody MovedCardDto movedCardDto) throws CardNotFoundException, DeckNotFoundException{

		Card card = cardService.moveCard(signedInUser, cardId, movedCardDto.getDeckId(),
				movedCardDto.getStandardCardId(), movedCardDto.getStandardType());

		return CommonResponse.success("CARD.MOVE", card);
	}
}
