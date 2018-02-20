package com.woowahan.woowahan2018.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Deck extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Deck deck = (Deck) o;
        return Objects.equals(name, deck.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                '}';
    }
}
