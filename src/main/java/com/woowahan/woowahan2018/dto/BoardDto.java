package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Board;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class BoardDto {
    private Long id;

    @NotNull(message = "이름을 입력하세요.")
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    public BoardDto() {
    }

    public BoardDto(String name) {
        this.name = name;
    }

    public BoardDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
