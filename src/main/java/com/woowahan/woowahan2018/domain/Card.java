package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Card extends AbstractEntity {

    @Column(nullable = false)
    private String text;

    private String description;

    private int orderIndex;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deck_id", foreignKey = @ForeignKey(name = "fk_deck_cards"))
    private Deck deck;

    public Card() {

    }

    public Card(String text, String description, Deck deck) {
        this.text = text;
        this.description = description;
        this.deck = deck;
        this.orderIndex = deck.getCardsSize();
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getDeckName() {
        return deck.getName();
    }

    public Card setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public Card setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    public Card moveTo(Deck deck) {
        this.deck = deck;
        return this;
    }

    public void checkMember(User signedInUser) {
        this.deck.checkMember(signedInUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return Objects.equals(text, card.text) &&
                Objects.equals(description, card.description) &&
                Objects.equals(deck, card.deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, description, deck);
    }

    @Override
    public String toString() {
        return "Card{" +
                "text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", orderIndex='" + orderIndex + '\'' +
                '}';
    }
}
