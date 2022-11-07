package com.solmaz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "posts", schema = "public")
@Getter
@Setter
@DynamicUpdate
public class Post {

    @Id
    @Column(name = "id")
    private String postId;

    private String topic;
    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime time = LocalDateTime.now();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "post",targetEntity = PostAttachement.class)
    private List<PostAttachement> attachements = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<UserPostReceiver> userPostReceivers = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<GroupPostReceiver> groupPostReceivers = new ArrayList<>();

    public Post() {
        this.postId = UUID.randomUUID().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return postId != null && Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
