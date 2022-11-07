package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "group_post_receivers", schema = "public")
@Getter
@Setter
public class GroupPostReceiver {
    @Id
    @Column(name = "id")
    private String groupPostReceiverId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Post post;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Group group;

    public GroupPostReceiver() {
        this.groupPostReceiverId = UUID.randomUUID().toString();
    }

    public GroupPostReceiver(Post post, Group group) {
        this();
        this.post = post;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupPostReceiver that = (GroupPostReceiver) o;
        return groupPostReceiverId != null && Objects.equals(groupPostReceiverId, that.groupPostReceiverId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
