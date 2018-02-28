package com.woowahan.woowahan2018.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Cards implements Serializable {
	@OneToMany(mappedBy = "deck")
	@OrderBy(value = "orderIndex")
	private List<Card> cards = new ArrayList<>();

	public Cards() {
	}

	public void add(int index, Card card) {
		cards.add(index, card);
	}

	public void add(Card card) {
		cards.add(card);
	}

	public void remove(Card card) {
		cards.remove(card);
	}

	public int indexOf(Card standardCard) {
		return cards.indexOf(standardCard);
	}

	public int size() {
		return cards.size();
	}

	public Card get(int i) {
		return cards.get(i);
	}

	public List<Card> getCards() {
		return cards;
	}
}

