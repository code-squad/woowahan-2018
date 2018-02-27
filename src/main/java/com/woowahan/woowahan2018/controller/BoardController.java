package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.BoardsDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.BoardNotFoundException;
import com.woowahan.woowahan2018.exception.ExistMemberExeption;
import com.woowahan.woowahan2018.exception.UserNotFoundException;
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
    public CommonResponse list(Principal principal) throws UserNotFoundException {
        String userEmail = principal.getName();
        List<Board> boards = userService.getBoardList(userEmail);

        return CommonResponse.success("Boards를 읽어왔습니다.",
                new BoardsDto(
                    boards.stream()
                    .map(Board::toBoardDto)
                    .collect(Collectors.toList()))
        );
    }

    @GetMapping("/{boardId}")
    public CommonResponse getOneBoard(Principal principal, @PathVariable long boardId) throws BoardNotFoundException, UserNotFoundException {
        User user = userService.findUserByEmail(principal.getName());
        Board board = boardService.findOneBoardForMember(user, boardId);
        
        return CommonResponse.success("Board를 읽어왔습니다.", board);
    }

    @PostMapping("")
    public CommonResponse createBoard(Principal principal,
                                      @RequestBody
                                      @Valid BoardDto boardDto) throws UserNotFoundException {
        log.debug("boardDto: {}", boardDto);
        User user = userService.findUserByEmail(principal.getName());
        Board board = boardService.createBoard(user, boardDto);
        return CommonResponse.success("Board를 성공적으로 생성했습니다.", board);
    }

    @PostMapping("/{boardId}/members")
    public CommonResponse addMember(Principal principal,
                                    @PathVariable long boardId,
                                    @RequestBody Map<String, String> params) throws BoardNotFoundException, UserNotFoundException, ExistMemberExeption {
        Board board = boardService.addMember(boardId, principal.getName(), params.get("email"));

        return CommonResponse.success("Member를 성공적으로 추가했습니다.", board.getMembers());
    }

}
