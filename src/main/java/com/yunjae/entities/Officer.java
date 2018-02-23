package com.yunjae.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by USER on 2018-02-23.
 */
@Data
@Document
public class Officer {
    @Id
    private String id;
    private Rank rank;
    private String first;
    private String last;

    public Officer() {}

    public Officer(Rank rank, String first, String last) {
        this.rank = rank;
        this.first = first;
        this.last = last;
    }

    public Officer(String id, Rank rank, String first, String last) {
        this.id = id;
        this.rank = rank;
        this.first = first;
        this.last = last;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", rank, first, last);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Officer)) return false;

        Officer officer = (Officer) o;

        if (!id.equals(officer.id)) return false;
        if (rank != officer.rank) return false;
        if (first != null ? !first.equals(officer.first) : officer.first != null) return false;
        return last.equals(officer.last);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + rank.hashCode();
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + last.hashCode();
        return result;
    }
}
