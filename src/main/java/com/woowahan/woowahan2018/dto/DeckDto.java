package com.woowahan.woowahan2018.dto;

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

    private List<Card> cards;

    public DeckDto() {

    }

    public String getName() {
        return name;
    }

    public DeckDto setName(String name) {
        this.name = name;
        return this;
    }

    public DeckDto setCards(List<Card> cards) {
        this.cards = cards;
        return this;
    }

    public Deck toDeck() {
        return new Deck(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckDto deckDto = (DeckDto) o;
        return Objects.equals(name, deckDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String
    toString() {
        return "DeckDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
