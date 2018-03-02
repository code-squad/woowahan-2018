package com.woowahan.woowahan2018.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment extends AbstractEntity {

    @Column(nullable = false)
    private String contents;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_comments"))
    private Card card;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_author_comments"))
    private User author;

    public Comment() {

    }

    public Comment(String contents, Card card, User author) {
        this.contents = contents;
        this.card = card;
        this.author = author;
    }

    public String getContents() {
        return contents;
    }

    public User getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getCreateDate() {
        return super.getFormattedCreateDate();
    }

    public Card getCard() {
        return card;
    }

    public void checkMember(User signedInUser) {
        if(!signedInUser.equals(author)) {
            throw new UnAuthorizedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(contents, comment.contents) &&
                Objects.equals(card, comment.card) &&
                Objects.equals(author, comment.author);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), contents, card, author);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "contents='" + contents + '\'' +
                ", card=" + card +
                ", author=" + author +
                '}';
    }
}
