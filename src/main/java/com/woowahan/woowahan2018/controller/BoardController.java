package com.woowahan.woowahan2018.controller;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.BoardsDto;
import com.woowahan.woowahan2018.dto.CommonResponse;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping("")
    private BoardsDto list() {
        List<Board> boards = boardService.findAllBoards();

        return new BoardsDto(
                boards.stream()
                .map(Board::toBoardDto)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("")
    public CommonResponse createBoard(@RequestBody @Valid BoardDto boardDto) throws MethodArgumentNotValidException {
        log.debug("boardDto: {}", boardDto);
        boardService.createBoard(boardDto);
        return CommonResponse.success("Board를 성공적으로 생성했습니다.");
    }
}
