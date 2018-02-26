package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_board_decks"))
    private Board board;

    public Deck(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void deleteCard(long cardId) {
        cards = cards.stream()
                .filter(card -> card.getId() != cardId)
                .collect(Collectors.toList());
    }

    public void addCard(long boardId, Card card) {
        checkBoard(boardId);
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

    public void checkBoard(long boardId) {
        if (board.matchId(boardId))
            throw new IllegalArgumentException("잘못된 접근입니다.");
    }
}
