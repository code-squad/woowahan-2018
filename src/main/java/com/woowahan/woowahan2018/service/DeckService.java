package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.CardRepository;
import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.domain.DeckRepository;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public Card createCard(long deckId, long boardId, CardDto cardDto) throws DeckNotFoundException {
        Card card = cardRepository.save(cardDto.toCard());

        Deck deck = findOneDeck(deckId);
        deck.addCard(boardId, card);

        return card;
    }

    public Deck findOneDeck(long deckId) throws DeckNotFoundException {
        return Optional.ofNullable(deckRepository.findOne(deckId))
                .orElseThrow(DeckNotFoundException::new);
    }
}
