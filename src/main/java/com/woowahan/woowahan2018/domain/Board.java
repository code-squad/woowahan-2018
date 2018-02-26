package com.woowahan.woowahan2018.domain;

import com.woowahan.woowahan2018.dto.BoardDto;
import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Board extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Deck> decks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_board_members",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<User> members = new ArrayList<>();

    public Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
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

    public void addMember(User user) {
        members.add(user);
    }

    public void addMember(UserDto userDto) {
        members.add(userDto.toUser());
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

    public void checkMember(User member) {
        if (!members.contains(member)) {
            throw new UnAuthorizedException("멤버가 아닙니다.");
        }
    }

    public boolean matchId(long boardId) {
        return getId() == boardId;
    }
}
