package com.woowahan.woowahan2018.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Deck;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CardDto {

    @NotBlank(message = "TEXT.EMPTY")
    private String text;

    private String description = "";

    private LocalDateTime dueDate;


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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public long getDeckId() {
        return deckId;
    }

    public CardDto setDeckId(long deckId) {
        this.deckId = deckId;
        return this;
    }

    public Card toCard(Deck deck) {
        return new Card(text, description, dueDate, deck);
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
