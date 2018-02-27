package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.CardNotFoundException;
import com.woowahan.woowahan2018.exception.DeckNotFoundException;
import com.woowahan.woowahan2018.exception.UnAuthenticationException;
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
    private BoardRepository boardRepository;

    public Deck findOneDeck(long deckId) throws DeckNotFoundException {
        return Optional.ofNullable(deckRepository.findOne(deckId))
                .orElseThrow(DeckNotFoundException::new);
    }

    @Transactional
    public Deck createDeck(User signedInUser, DeckDto deckDto) throws BoardNotFoundException {
        long boardId = deckDto.getBoardId();
        Board board = Optional.ofNullable(boardRepository.findOne(boardId))
                .orElseThrow(BoardNotFoundException::new);

        board.checkOwner(signedInUser);
        return deckRepository.save(deckDto.toDeck(board));
    }
}
