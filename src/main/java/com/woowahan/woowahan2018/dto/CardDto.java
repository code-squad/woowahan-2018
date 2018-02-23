package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Deck;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

public class CardDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String text;

    public CardDto() {

    }

    public String getText() {
        return text;
    }

    public CardDto setText(String text) {
        this.text = text;
        return this;
    }

    public Card toCard() {
        return new Card(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDto cardDto = (CardDto) o;
        return Objects.equals(text, cardDto.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "text='" + text + '\'' +
                '}';
    }
}
