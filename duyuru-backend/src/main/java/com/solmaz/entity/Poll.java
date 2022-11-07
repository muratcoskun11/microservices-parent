package com.solmaz.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "poll", schema = "public")
@Getter
@Setter
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String pollId;
    private String question;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User publisher;
    @CreatedDate
    private LocalDateTime time = LocalDateTime.now();
    private LiveTime liveTime;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "poll",targetEntity = PollOption.class)
    private List<PollOption> pollOptions;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "poll",targetEntity = UserPollReceiver.class)
    private List<UserPollReceiver> userPollReceivers = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "poll",targetEntity = GroupPollReceiver.class)
    private List<GroupPollReceiver> groupPollReceivers =new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return Objects.equals(pollId, poll.pollId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollId);
    }

    @Override
    public String toString() {
        return "Poll{" +
                "pollId='" + pollId + '\'' +
                ", question='" + question + '\'' +
                ", publisher=" + publisher +
                ", time=" + time +
                ", liveTime=" + liveTime +
                '}';
    }
}
