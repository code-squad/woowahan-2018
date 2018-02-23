package com.woowahan.woowahan2018.dto;

import java.util.List;

public class BoardsDto {
    private List<BoardDto> boards;

    public BoardsDto() {
    }

    public BoardsDto(List<BoardDto> boards) {
        this.boards = boards;
    }

    public List<BoardDto> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardDto> boards) {
        this.boards = boards;
    }
}
