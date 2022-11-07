package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_post_receivers", schema = "public")
@Getter
@Setter
public class UserPostReceiver {

    @Id
    @Column(name = "id")
    private String userPostReceiverId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Post post;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private User user;

    public UserPostReceiver() {
        this.userPostReceiverId = UUID.randomUUID().toString();
    }

    public UserPostReceiver(Post post, User user) {
        this();
        this.post = post;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserPostReceiver that = (UserPostReceiver) o;
        return userPostReceiverId != null && Objects.equals(userPostReceiverId, that.userPostReceiverId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}