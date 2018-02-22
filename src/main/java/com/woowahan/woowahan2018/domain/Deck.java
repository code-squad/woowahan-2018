package com.woowahan.woowahan2018.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Deck extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deck_cards"))
    private List<Card> cards = new ArrayList<>();

    public Deck() {

    }

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void deleteCard(long cardId) {
        cards = cards.stream()
                .filter(card -> card.getId() != cardId)
                .collect(Collectors.toList());
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Deck deck = (Deck) o;
        return Objects.equals(name, deck.name) &&
                Objects.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, cards);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}
