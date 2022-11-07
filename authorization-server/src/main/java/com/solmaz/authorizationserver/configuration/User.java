package com.solmaz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@DynamicUpdate
public class User {

    @Id
    @Column(name = "id")
    private String userId;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(name = "unvan")
    private String title;
    @Column(name = "photo_url")
    private String photoUrl;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "creator",targetEntity = Group.class)
    private List<Group> groups;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "publisher",targetEntity = Poll.class)
    private List<Poll> polls;
    @JsonIgnore
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<UserPostReceiver> postReceiverEntities = new ArrayList<>();
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seenByUser"
    )
    private List<SeePost> seePost;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user"
    )
    private List<Bookmark> bookmarks;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user"
    )
    private List<GroupMember> groupMembers;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Post> posts;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",targetEntity = PrivatePost.class)
    private List<PrivatePost> privatePosts;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<UserPrivatePostRecevier> userPrivatePostReceviers;
    @JsonIgnore
    @Column(name = "pin")
    private String privateMessagePin;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<UserPollReceiver> userPollReceiver;
    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

