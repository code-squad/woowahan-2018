package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Deck;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeckDto {

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 20, message = "길이제한: 1~20자")
    private String name;

    private long boardId;

    public DeckDto() {

    }

    public String getName() {
        return name;
    }

    public DeckDto setName(String name) {
        this.name = name;
        return this;
    }

    public long getBoardId() {
        return boardId;
    }

    public DeckDto setBoardId(long boardId) {
        this.boardId = boardId;
        return this;
    }

    public Deck toDeck(Board board) {
        return new Deck(name, board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckDto deckDto = (DeckDto) o;
        return boardId == deckDto.boardId &&
                Objects.equals(name, deckDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, boardId);
    }

    @Override
    public String toString() {
        return "DeckDto{" +
                "name='" + name + '\'' +
                ", boardId=" + boardId +
                '}';
    }
}
