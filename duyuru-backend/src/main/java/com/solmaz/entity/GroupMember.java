package com.solmaz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "group_members", schema = "public")
@Getter
@Setter
public class GroupMember {
    @Id
    @Column(name = "id")
    private String groupMemberId;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private User user;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    private Group group;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE},mappedBy = "groupMember",targetEntity = GroupmemberPollOption.class)
    private List<GroupmemberPollOption> groupmemberPollOption;
    @CreatedDate
    private LocalDateTime time = LocalDateTime.now();

    public GroupMember() {
        this.groupMemberId = UUID.randomUUID().toString();
    }

    public GroupMember(User user, Group group) {
        this();
        this.user = user;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupMember that = (GroupMember) o;
        return groupMemberId != null && Objects.equals(groupMemberId, that.groupMemberId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
