package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.*;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.ExistMemberExeption;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
import com.woowahan.woowahan2018.security.SignedInUser;
import com.woowahan.woowahan2018.service.BoardService;
import com.woowahan.woowahan2018.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public CommonResponse list(@SignedInUser User signedInUser) throws UserNotFoundException {
        String userEmail = signedInUser.getEmail();
        List<Board> boards = userService.getBoardList(userEmail);

        return CommonResponse.success("BOARD.READ_MULTIPLE", boards);
    }

    @GetMapping("/{boardId}")
    public CommonResponse getOneBoard(@SignedInUser User signedInUser, @PathVariable long boardId) throws BoardNotFoundException, UserNotFoundException {
        User user = userService.findUserByEmail(signedInUser.getEmail());
        Board board = boardService.findOneBoardForMember(user, boardId);
        
        return CommonResponse.success("BOARD.READ_SINGLE", board);
    }

    @PostMapping("")
    public CommonResponse createBoard(@SignedInUser User signedInUser,
                                      @RequestBody
                                      @Valid BoardDto boardDto) throws UserNotFoundException {
        log.debug("boardDto: {}", boardDto);
        User user = userService.findUserByEmail(signedInUser.getEmail());
        Board board = boardService.createBoard(user, boardDto);
        return CommonResponse.success("BOARD.CREATE", board);
    }

    @PostMapping("/{boardId}/members")
    public CommonResponse addMember(@SignedInUser User signedInUser,
                                    @PathVariable long boardId,
                                    @RequestBody MemberDto memberDto) throws BoardNotFoundException, UserNotFoundException, ExistMemberExeption {
        Board board = boardService.addMember(boardId, signedInUser.getEmail(), memberDto.getEmail());

        return CommonResponse.success("MEMBER.ADD", board.getMembers());
    }

}
