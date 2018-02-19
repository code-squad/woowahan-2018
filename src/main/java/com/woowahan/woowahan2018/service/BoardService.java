package com.woowahan.woowahan2018.service;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.BoardRepository;
import com.woowahan.woowahan2018.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void createBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toBoard());
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
