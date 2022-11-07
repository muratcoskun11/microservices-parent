package com.solmaz.repository;

import com.solmaz.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll,String> {
}
