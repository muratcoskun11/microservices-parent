package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "bookmarks", schema = "public")
@Getter
@Setter
public class Bookmark {
    @Id
    @Column(name = "id")
    private String bookmarkId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Post post;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private User user;


    public Bookmark() {
        this.bookmarkId = UUID.randomUUID().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bookmark bookmark = (Bookmark) o;
        return bookmarkId != null && Objects.equals(bookmarkId, bookmark.bookmarkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
