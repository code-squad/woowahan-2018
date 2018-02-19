package com.woowahan.woowahan2018.domain;

import com.woowahan.woowahan2018.dto.BoardDto;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Board extends AbstractEntity{
    private String name;

    public Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BoardDto toBoardDto() {
        return new BoardDto(super.getId(), name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        if (!super.equals(o)) return false;
        Board board = (Board) o;
        return Objects.equals(name, board.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Board{" +
                "name='" + name + '\'' +
                '}';
    }
}
