package com.solmaz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "groups", schema = "public")
@Getter
@Setter
public class Group {
    @Id
    @Column(name = "id")
    private String groupId;
    private String name;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "creator")
    private User creator;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group",fetch = FetchType.LAZY)
    private List<GroupMember> groupMemberList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group",fetch = FetchType.LAZY)
    private List<GroupPostReceiver> groupPostReceiverList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group",fetch = FetchType.LAZY)
    private List<GroupPrivatePostRecevier> groupPrivatePostReceviers;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group",fetch = FetchType.LAZY)
    private List<GroupPollReceiver> groupPollReceivers;
    public Group() {
        this.groupId = UUID.randomUUID().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return groupId != null && Objects.equals(groupId, group.groupId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
