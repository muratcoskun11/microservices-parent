package com.solmaz.repository;

import com.solmaz.entity.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollOptionRepository extends JpaRepository<PollOption,Long> {
}
