package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Board;
import com.woowahan.woowahan2018.dto.group.name.NameSecondGroup;
import com.woowahan.woowahan2018.dto.group.name.NameFirstGroup;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Objects;

public class BoardDto {

    @NotBlank(message = "TEXT.EMPTY", groups = NameFirstGroup.class)
    @Length(min = 1, max = 20, groups = NameSecondGroup.class)
    private String name;

    public BoardDto() {
    }

    public BoardDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board toBoard() {
        return new Board(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardDto)) return false;
        BoardDto boardDto = (BoardDto) o;
        return Objects.equals(name, boardDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
