package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "group_private_post_receivers", schema = "public")
@Getter
@Setter
public class GroupPrivatePostRecevier {
    @Id
    @Column(name = "id")
    private String groupPostReceiverId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private PrivatePost post;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Group group;

    public GroupPrivatePostRecevier() {
        this.groupPostReceiverId = UUID.randomUUID().toString();
    }

    public GroupPrivatePostRecevier(PrivatePost post, Group group) {
        this();
        this.post = post;
        this.group = group;
    }
}
