package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Deck extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Deck.class);

	@Column(nullable = false)
	private String name;

	@Embedded
	private Cards cards = new Cards();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_board_decks"))
	private Board board;

	public Deck() {
	}

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
		return cards.getCards();
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

	public void checkMember(User signedInUser) {
		board.checkOwner(signedInUser);
	}

	public void sort(Card card) {
		card.setOrderIndex(getCardsSize());
		cards.add(card);
	}

	public void sort(Card card, Card standardCard, boolean standardType) {
		cards.remove(card);
		int orderIndex = cards.indexOf(standardCard) + (standardType ? 0 : 1);
		cards.add(orderIndex, card);

		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setOrderIndex(i);
		}
	}

	public int getCardsSize() {
		return cards.size();
	}
}
