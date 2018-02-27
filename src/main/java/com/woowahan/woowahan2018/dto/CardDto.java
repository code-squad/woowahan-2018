package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Deck;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

public class CardDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String text;

    private String description = "";

    private long deckId;

    public CardDto() {

    }

    public String getText() {
        return text;
    }

    public CardDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CardDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getDeckId() {
        return deckId;
    }

    public CardDto setDeckId(long deckId) {
        this.deckId = deckId;
        return this;
    }

    public Card toCard(Deck deck) {
        return new Card(text, description, deck);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDto cardDto = (CardDto) o;
        return deckId == cardDto.deckId &&
                Objects.equals(text, cardDto.text) &&
                Objects.equals(description, cardDto.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text, description, deckId);
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", deckId=" + deckId +
                '}';
    }
}
