package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.*;
import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.ExistMemberException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Board createBoard(User user, BoardDto boardDto) {
        Board board= boardDto.toBoard();
        board.addMember(user);
        return boardRepository.save(board);
    }

    public Board findOneBoardForMember(User member, long boardId) throws BoardNotFoundException {
        Board board = findOneBoard(boardId);
        board.checkOwner(member);

        return board;
    }

    private Board findOneBoard(long boardId) throws BoardNotFoundException {
        return Optional.ofNullable(boardRepository.findOne(boardId))
                .orElseThrow(BoardNotFoundException::new);
    }

    @Transactional
    public Board addMember(long boardId, String ownerEmail, String memberEmail) throws BoardNotFoundException, UserNotFoundException, ExistMemberException {
        User signedInUser = userService.findUserByEmail(ownerEmail);
        Board board = findOneBoardForMember(signedInUser, boardId);
        User user = userRepository.findByEmail(memberEmail)
                .orElseThrow(UserNotFoundException::new);
        board.checkMember(user);

        return board.addMember(user);
    }
}
