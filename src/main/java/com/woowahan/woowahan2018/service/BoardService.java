package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.BoardRepository;
import com.woowahan.woowahan2018.domain.Deck;
import com.woowahan.woowahan2018.domain.DeckRepository;
import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.exception.NoSuchBoardFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private DeckRepository deckRepository;

    public void createBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toBoard());
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public List<Deck> findAllDecks(long boardId) throws NoSuchBoardFoundException {
        return Optional.ofNullable(boardRepository.findOne(boardId))
                .map(Board::getDecks)
                .orElseThrow(NoSuchBoardFoundException::new);
    }

    @Transactional
    public void createDeck(long boardId, DeckDto deckDto) throws NoSuchBoardFoundException {
        Deck deck = deckRepository.save(deckDto.toDeck());

        Board board = Optional.ofNullable(boardRepository.findOne(boardId))
                .orElseThrow(NoSuchBoardFoundException::new);

        board.addDeck(deck);
    }

    @Transactional
    public void deleteDeck(long boardId, long deckId) throws NoSuchBoardFoundException {
        Board board = Optional.ofNullable(boardRepository.findOne(boardId))
                .orElseThrow(NoSuchBoardFoundException::new);

        board.deleteDeck(deckId);
        deckRepository.delete(deckId);
    }
}
