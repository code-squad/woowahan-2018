package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

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
    public Card updateCardDescription(User signedInUser, long cardId, String description) throws DeckNotFoundException, CardNotFoundException, UnAuthenticationException {
        Card card = findOneCardForMember(signedInUser, cardId);
        card.setDescription(description);

        return card;
    }

    public Card findOneCardForMember(User signedInUser, long cardId) throws CardNotFoundException, DeckNotFoundException {
        Card card = findOneCard(cardId);
        card.checkMember(signedInUser);

        return card;
    }

    private Card findOneCard(long cardId) throws DeckNotFoundException, CardNotFoundException {
        return Optional.ofNullable(cardRepository.findOne(cardId))
                .orElseThrow(CardNotFoundException::new);
    }
}
