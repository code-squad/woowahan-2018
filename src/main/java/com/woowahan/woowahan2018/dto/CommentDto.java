package com.woowahan.woowahan2018.dto;

import com.woowahan.woowahan2018.domain.Card;
import com.woowahan.woowahan2018.domain.Comment;
import com.woowahan.woowahan2018.domain.User;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CommentDto {

    @NotBlank(message = "TEXT.EMPTY")
    @Size(max = 255, message = "COMMENT.LENGTH")
    private String contents;

    private long cardId;

    private long userId;

    public CommentDto() {

    }

    public String getContents() {
        return contents;
    }

    public CommentDto setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public CommentDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getCardId() {
        return cardId;
    }

    public CommentDto setCardId(long cardId) {
        this.cardId = cardId;
        return this;
    }

    public Comment toComment(Card card, User author) {
        return new Comment(contents, card, author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return cardId == that.cardId &&
                userId == that.userId &&
                Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {

        return Objects.hash(contents, cardId, userId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "contents='" + contents + '\'' +
                ", cardId=" + cardId +
                ", userId=" + userId +
                '}';
    }
}
