package com.solmaz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_poll_receivers", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPollReceiver {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String pollReceiverId;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    private User user;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    private Poll poll;
}
