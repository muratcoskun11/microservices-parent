package com.solmaz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "private_posts", schema = "public")
@Getter
@Setter
@DynamicUpdate
public class PrivatePost {
    @Id
    @Column(name = "id")
    private String postId;

    private String topic;
    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime time = LocalDateTime.now();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = PrivatePostAttachement.class,mappedBy = "privatePost")
    private List<PrivatePostAttachement> attachements = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<UserPrivatePostRecevier> privateUserPostReceivers = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<GroupPrivatePostRecevier> groupPrivatePostReceviers = new ArrayList<>();

    public PrivatePost() {
        this.postId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivatePost that = (PrivatePost) o;
        return Objects.equals(postId, that.postId);
    }

    @Override
    public String toString() {
        return "PrivatePost{" +
                "postId='" + postId + '\'' +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

}
