package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "see_post", schema = "public")
@Getter
@Setter
public class SeePost {

    @Id
    @Column(name = "id")
    private String seePostId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private User seenByUser;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Post post;
    @CreatedDate
    private LocalDateTime time = LocalDateTime.now();

    public SeePost() {
        this.seePostId = UUID.randomUUID().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeePost seePost = (SeePost) o;
        return seePostId != null && Objects.equals(seePostId, seePost.seePostId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
