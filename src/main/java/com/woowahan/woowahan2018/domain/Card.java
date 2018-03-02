package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.woowahan2018.dto.CardDto;
import com.woowahan.woowahan2018.exception.ExistMemberException;
import com.woowahan.woowahan2018.exception.UserNotFoundException;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Card extends AbstractEntity {

    @Column(nullable = false)
    private String text;

    private String description;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;

    @OneToMany(mappedBy = "card")
    private List<Comment> comments = new ArrayList<>();
    private int orderIndex;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deck_id", foreignKey = @ForeignKey(name = "fk_deck_cards"))
    private Deck deck;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_card_assignee",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "assignee_id"))
    private List<User> assignees = new ArrayList<>();

    public Card() {
    }

    public Card(String text, String description, LocalDateTime dueDate, Deck deck) {
        this.text = text;
        this.description = description;
        this.dueDate = dueDate;
        this.deck = deck;
        this.orderIndex = deck.getCardsSize();
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void addAssignees(User signedInUser, User assignee) throws ExistMemberException {
        checkMember(signedInUser);
        checkMember(assignee);
        if (assignees.contains(assignee))
            throw new ExistMemberException();
        assignees.add(assignee);
    }

    public void deleteAssignees(User signedInUser, User assignee) throws UserNotFoundException {
        checkMember(signedInUser);
        checkMember(assignee);
        if (!assignees.contains(assignee)) {
            throw new UserNotFoundException();
        }
        assignees.remove(assignee);
    }

    public void checkBoardMember(User user) {
        deck.checkMember(user);
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
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

    public List<Comment> getComments() {
        return comments;
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

    public void updateDescription(User signedInUser, CardDto cardDto) {
        checkMember(signedInUser);
        this.description = cardDto.getDescription();
    }

    public void updateDueDate(User signedInUser, CardDto cardDto) {
        checkMember(signedInUser);
        this.dueDate = cardDto.getDueDate();
    }

    public List<User> getMembers() {
        return deck.getMembers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return orderIndex == card.orderIndex &&
                Objects.equals(text, card.text) &&
                Objects.equals(description, card.description) &&
                Objects.equals(dueDate, card.dueDate) &&
                Objects.equals(comments, card.comments) &&
                Objects.equals(deck, card.deck) &&
                Objects.equals(assignees, card.assignees);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), text, description, dueDate, comments, orderIndex, deck, assignees);
    }

    @Override
    public String toString() {
        return "Card{" +
                "text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", comments=" + comments +
                ", orderIndex=" + orderIndex +
                ", deck=" + deck +
                ", assignees=" + assignees +
                '}';
    }
}
