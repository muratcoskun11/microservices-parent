package com.solmaz.repository;

import com.solmaz.entity.UserPollReceiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPollReceiverRepository extends JpaRepository<UserPollReceiver,String> {
    Optional<UserPollReceiver> findByUserUserIdAndPollPollId(String userId, String pollId);
}
