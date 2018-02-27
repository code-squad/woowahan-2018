package com.woowahan.woowahan2018.domain;

import com.woowahan.woowahan2018.dto.UserDto;
import com.woowahan.woowahan2018.exception.ExistMemberExeption;
import com.woowahan.woowahan2018.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        if (!super.equals(o)) return false;
        Board board = (Board) o;
        return Objects.equals(name, board.name);
    }

    public Board addMember(User user) {
        members.add(user);
        return this;
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

    public void checkOwner(User member) {
        if (!members.contains(member)) {
            throw new UnAuthorizedException("멤버가 아닙니다.");
        }
    }

    public void checkMember(User user) throws ExistMemberExeption {
        if (members.contains(user)) {
            throw new ExistMemberExeption();
        }
    }

    public boolean matchId(long boardId) {
        return getId() == boardId;
    }
}
