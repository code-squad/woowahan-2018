package com.woowahan.woowahan2018.dto;

public class MovedCardDto {
	long deckId;
	long standardCardId;
	boolean standardType;

	public MovedCardDto() {
	}

	public MovedCardDto(long deckId, long standardCardId, boolean standardType) {
		this.deckId = deckId;
		this.standardCardId = standardCardId;
		this.standardType = standardType;
	}

	public long getDeckId() {
		return deckId;
	}

	public MovedCardDto setDeckId(long deckId) {
		this.deckId = deckId;
		return this;
	}

	public long getStandardCardId() {
		return standardCardId;
	}

	public MovedCardDto setStandardCardId(long standardCardId) {
		this.standardCardId = standardCardId;
		return this;
	}

	public boolean getStandardType() {
		return standardType;
	}

	public MovedCardDto setAbove(boolean standardType) {
		this.standardType = standardType;
		return this;
	}

	@Override
	public String toString() {
		return "MovedCardDto{" +
				"deckId=" + deckId +
				", standardCardId=" + standardCardId +
				", standardType=" + standardType +
				'}';
	}
}
