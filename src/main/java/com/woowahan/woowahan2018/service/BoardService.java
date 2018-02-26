package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.DeckDto;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Transactional
    public Board createBoard(User user, BoardDto boardDto) {
        Board board= boardDto.toBoard();
        board.addMember(user);
        return boardRepository.save(board);
    }

    public List<Deck> findAllDecks(long boardId) throws BoardNotFoundException {
        Board board = findOneBoard(boardId);
        return board.getDecks();
    }

    @Transactional
    public Deck createDeck(long boardId, DeckDto deckDto) throws BoardNotFoundException {
        Board board = findOneBoard(boardId);
        deckDto.setBoard(board);
        Deck deck = deckRepository.save(deckDto.toDeck());

        return deck;
    }

    public Board findOneBoardForMember(User member, long boardId) throws BoardNotFoundException {
        Board board = findOneBoard(boardId);
        board.checkMember(member);

        return board;
    }

    private Board findOneBoard(long boardId) throws BoardNotFoundException {
        return Optional.ofNullable(boardRepository.findOne(boardId))
                .orElseThrow(BoardNotFoundException::new);
    }

    public void addMember(long boardId, UserDto userDto) throws BoardNotFoundException {
        Board board = findOneBoard(boardId);
        board.addMember(userDto);
    }
}
