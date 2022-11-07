package com.solmaz.repository;

import com.solmaz.entity.SelectedOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelectedOptionRepository extends JpaRepository<SelectedOption,String> {
    @Query(value = "select count(s) from SelectedOption s,Poll p " +
            "where s.option.poll.pollId=?1 and s.option.option=?2  ")
    long getNumberOfSelectors(String pollId,String option);
}
