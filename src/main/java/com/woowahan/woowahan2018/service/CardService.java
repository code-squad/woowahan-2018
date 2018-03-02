package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.exception.ExistMemberException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardService {
    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Card createCard(User signedInUser, long deckId, CardDto cardDto) throws DeckNotFoundException {
        Deck deck = Optional.ofNullable(deckRepository.findOne(deckId))
                .orElseThrow(DeckNotFoundException::new);
        deck.checkMember(signedInUser);
        Card card = cardRepository.save(cardDto.toCard(deck));
        deck.addCard(card);
        return card;
    }

    @Transactional
    public Card updateCardDescription(User signedInUser, long cardId, CardDto cardDto) throws CardNotFoundException {
        cardDto.setDescription(cardDto.getDescription());

        Card card = findOneCard(cardId);
        card.updateDescription(signedInUser, cardDto);

        return card;
    }

    @Transactional
    public Card updateCardDate(User signedInUser, long cardId, CardDto cardDto) throws CardNotFoundException {
        cardDto.setDueDate(cardDto.getDueDate());

        Card card = findOneCard(cardId);
        card.updateDueDate(signedInUser, cardDto);

        return card;
    }

    @Transactional
    public Card addAssignee(User signedInUser, long cardId, String email) throws CardNotFoundException, UserNotFoundException, ExistMemberException {
        Card card = findOneCard(cardId);
        User assignee = userService.findUserByEmail(email);
        card.addAssignees(signedInUser, assignee);

        return card;
    }

    @Transactional
    public Card deleteAssignee(User signedInUser, long cardId, String email) throws CardNotFoundException,  UserNotFoundException {
        Card card = findOneCard(cardId);
        User assignee = userService.findUserByEmail(email);
        card.deleteAssignees(signedInUser, assignee);
        return card;
    }

    @Transactional
    public Card moveCard(User signedInUser, long cardId, long deckId, long standardCardId, boolean standardType) throws CardNotFoundException {
        Card card = findOneCard(cardId);
        card.checkMember(signedInUser);

        Deck deck = deckRepository.findOne(deckId);
        card.moveTo(deck);

        try {
            Card standardCard = findOneCard(standardCardId);
            deck.sort(card, standardCard, standardType);
        } catch (CardNotFoundException e) {
            deck.sort(card);
        }
        return card;
    }

    public Card findOneCardForMember(User signedInUser, long cardId) throws CardNotFoundException {
        Card card = findOneCard(cardId);
        card.checkMember(signedInUser);
        return card;
    }

    private Card findOneCard(long cardId) throws CardNotFoundException {
        return Optional.ofNullable(cardRepository.findOne(cardId))
                .orElseThrow(CardNotFoundException::new);
    }

    public Map<String, List<User>> getAssignees(User signedInUser, long cardId) throws CardNotFoundException {
        Card card = findOneCardForMember(signedInUser, cardId);
        List<User> assignees = card.getAssignees();
        List<User> boardMembers = card.getMembers();

        Map<String, List<User>> result = new HashMap<>();
        result.put("assignees", assignees);
        result.put("boardMembers", boardMembers);
        assignees.forEach(assignee -> {
            boardMembers.remove(assignee);
        });

        return result;
    }
}
