package com.woowahan.woowahan2018.domain;

import com.woowahan.woowahan2018.dto.BoardDto;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Board extends AbstractEntity{
    private String name;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_decks"))
    private List<Deck> decks;

    public Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Deck> getDecks() {
        return this.decks;
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

    public void deleteDeck(long deckId) {
        decks = decks.stream()
                .filter(deck -> deck.getId() != deckId)
                .collect(Collectors.toList());
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }
}
