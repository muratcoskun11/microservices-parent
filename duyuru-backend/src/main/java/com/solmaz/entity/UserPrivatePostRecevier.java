package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_private_post_receivers", schema = "public")
@Getter
@Setter
public class UserPrivatePostRecevier {
    @Id
    @Column(name = "id")
    private String userPrivatePostReceiverId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private PrivatePost post;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private User user;

    public UserPrivatePostRecevier() {
        this.userPrivatePostReceiverId = UUID.randomUUID().toString();
    }

    public UserPrivatePostRecevier(PrivatePost post, User user) {
        this();
        this.post = post;
        this.user = user;
    }
}
